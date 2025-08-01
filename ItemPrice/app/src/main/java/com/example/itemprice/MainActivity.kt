package com.example.itemprice

import android.os.Bundle
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
    private val sheetId = "14wT99Sbcvo9ME6TlXh_EZq2LGCV05gcLf93X9hEWXNA" // Replace with your actual Google Sheet ID
    private val apiKey = "AIzaSyArFjc75z3lM-NQMsmgFpzkBqUhbHLunzU"  // Replace with your API Key
    private val range = "Sheet1!A2:B" // Range where your data is

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from Google Sheets
        fetchDataFromGoogleSheets()
    }

    private fun fetchDataFromGoogleSheets() {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sheets.googleapis.com/v4/spreadsheets/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val googleSheetsApi = retrofit.create(GoogleSheetsApi::class.java)

        // Make the request
        googleSheetsApi.getSheetData(sheetId, range, apiKey).enqueue(object : Callback<SheetResponse> {
            override fun onResponse(call: Call<SheetResponse>, response: Response<SheetResponse>) {
                if (response.isSuccessful) {
                    // Extract data from the response
                    val sheetData = response.body()?.values
                    val itemPriceList = mutableListOf<ItemPrice>()

                    // Parse the sheet data
                    sheetData?.forEach { row ->
                        if (row.size >= 2) {
                            val item = row[0]  // Item name
                            val price = row[1].toDoubleOrNull() ?: 0.0  // Price, default to 0 if not valid
                            itemPriceList.add(ItemPrice(item, price))
                        }
                    }

                    // Set up the RecyclerView adapter with the data
                    itemPriceAdapter = ItemPriceAdapter(itemPriceList)
                    recyclerView.adapter = itemPriceAdapter
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
