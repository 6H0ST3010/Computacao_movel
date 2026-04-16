package dam_A15318.coolweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.res.Configuration
import java.net.URL
import java.io.InputStreamReader
import com.google.gson.Gson
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.util.Log

class MainActivity : AppCompatActivity() {
    private var day = if (java.util.Calendar.getInstance().get(
            java.util.Calendar.HOUR_OF_DAY
        ) in 6..18) 1 else 0 //0-night / 1-day

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val refreshButton: Button = findViewById(R.id.button2)
        refreshButton.setOnClickListener {
            val latEdit = findViewById<EditText>(R.id.id_lat2)
            val lonEdit = findViewById<EditText>(R.id.id_long2)
            val lat = latEdit.text.toString().toFloatOrNull() ?: 38.75f
            val lon = lonEdit.text.toString().toFloatOrNull() ?: -9.125f
            fetchWeatherData(lat, lon).start()
        }
        fetchWeatherData(38.75f, -9.125f).start()
    }

    private fun updateUI(request: WeatherData) {
        runOnUiThread {
            val timeString = request.current_weather.time  // e.g. "2024-06-10T14:00"
            val hour = timeString.substring(11, 13).toInt()
            val newDay = if (hour in 6..18) 1 else 0
            if (newDay != day) {
                day = newDay
                applyTheme()
                recreate()
                return@runOnUiThread
            }
            val weatherImage: ImageView = findViewById(R.id.weatherImage)
            var latText: EditText = findViewById(R.id.id_lat2)
            var longText: EditText = findViewById(R.id.id_long2)
            val pressureText: TextView = findViewById(R.id.id_sea2)
            val windSpeedText: TextView = findViewById(R.id.id_wind2)
            val windDirectionText: TextView = findViewById(R.id.id_wind4)
            val temperatureText: TextView = findViewById(R.id.id_temp2)
            val timeText: TextView = findViewById(R.id.id_time2)

            latText.setText(request.latitude)
            longText.setText(request.longitude)

            pressureText.text = request.hourly.pressure_msl.get (12).toString() + " hPa"

            windSpeedText.text = request.current_weather.windspeed.toString() + " km/h"
            windDirectionText.text = request.current_weather.winddirection.toString() + "°"
            temperatureText.text = request.current_weather.temperature.toString() + "°C"
            timeText.text = request.current_weather.time

                // Update weather image
            val weatherMap = getWeatherCodeMap()
            val wCode = weatherMap.get(request.current_weather.weathercode)

            val wImage = when (wCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY -> {
                    if (day == 1) {
                        wCode?.image + "day"
                    } else {
                        wCode?.image + "night"
                    }
                }
                    else -> {
                        wCode?.image
                    }
                }
                val cleanImageName = wImage?.trim()?.replace(" ", "")
                val res = getResources()
                val resID = res.getIdentifier(cleanImageName, "drawable", packageName)
                if (resID != 0) {
                    val drawable = this.getDrawable(resID)
                    weatherImage.setImageDrawable(drawable)
                } else {
                    weatherImage.setImageResource(R.drawable.fog)
                }

            }
        }


        private fun WeatherAPI_Call(lat: Float, long: Float): WeatherData? {
                // Remove spaces and build URL correctly
                val reqString = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${long}&current_weather=true&hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m"
                val url = URL ( reqString) ;
                url . openStream () . use {
                    val request = Gson () . fromJson ( InputStreamReader (it ,"UTF-8") ,WeatherData :: class . java )
                    return request
                }
            }
    private fun fetchWeatherData(lat: Float, long: Float): Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            if (weather != null) {
                updateUI(weather)
            }
        }
    }

    private fun applyTheme() {
        val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val themeRes = when {
            isPortrait && day == 1 -> R.style.Theme_Day
            isPortrait && day == 0 -> R.style.Theme_Night
            !isPortrait && day == 1 -> R.style.Theme_Day_Land
            else -> R.style.Theme_Night_Land
        }

        setTheme(themeRes)
    }
}