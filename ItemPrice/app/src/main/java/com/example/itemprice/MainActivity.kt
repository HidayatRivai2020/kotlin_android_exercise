package com.example.itemprice

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemPriceAdapter: ItemPriceAdapter
    private val sheetId = BuildConfig.GOOGLE_SHEET_ID
    private val apiKey = BuildConfig.GOOGLE_API_KEY
    private val range = "Sheet1!A2:B" // Range where your data is

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://sheets.googleapis.com/v4/spreadsheets/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(GoogleSheetsApi::class.java)

        api.getSheetData(sheetId, range, apiKey).enqueue(object : Callback<SheetResponse> {
            override fun onResponse(call: Call<SheetResponse>, response: Response<SheetResponse>) {
                if (response.isSuccessful) {
                    val itemList = response.body()?.values?.mapNotNull {
                        if (it.size >= 2) ItemPrice(it[0], it[1].toDoubleOrNull() ?: 0.0) else null
                    } ?: listOf()

                    itemPriceAdapter = ItemPriceAdapter(itemList)
                    recyclerView.adapter = itemPriceAdapter

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean = false
                        override fun onQueryTextChange(newText: String?): Boolean {
                            itemPriceAdapter.filter.filter(newText)
                            return true
                        }
                    })

                } else {
                    Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SheetResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
