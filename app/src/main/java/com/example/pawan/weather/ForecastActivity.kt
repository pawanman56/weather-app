package com.example.pawan.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        var retriever = WeatherRetriever()

        val callback = object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                println("It's working")
                title = response?.body()?.query?.results?.channel?.title

                var forecasts = response?.body()?.query?.results?.channel?.item?.forecast
                var forecastString = mutableListOf<String>()

                if (forecasts != null) {
                    for (forecast in forecasts) {
                        val newString = "${forecast.date} - High: ${forecast.high}, Low: ${forecast.low}, ${forecast.text}"
                        forecastString.add(newString)
                    }
                }

                var listView = findViewById<ListView>(R.id.forecastListView)
                var adapter = ArrayAdapter(this@ForecastActivity, android.R.layout.simple_expandable_list_item_1, forecastString)

                listView.adapter = adapter
            }

            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                println("Error")
            }

        }

        val searchTerm = intent.extras.getString("searchTerm")
        retriever.getForecast(callback, searchTerm)
    }
}
