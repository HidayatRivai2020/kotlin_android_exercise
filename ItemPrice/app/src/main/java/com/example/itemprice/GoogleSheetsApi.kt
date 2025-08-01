package com.example.itemprice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleSheetsApi {
    @GET("{sheetId}/values/{range}")
    fun getSheetData(
        @Path("sheetId") sheetId: String,
        @Path("range") range: String,
        @Query("key") apiKey: String
    ): Call<SheetResponse>
}
