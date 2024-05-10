package uz.umarov.httprequestwithrscript

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.umarov.httprequestwithrscript.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener {
            val numbersString = binding.editTextNumbers.text.toString()
            CalculateMeanTask().execute(numbersString)
        }
    }

    inner class CalculateMeanTask : AsyncTask<String, Void, Double>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): Double {
            val numbersString = params[0]

            val url = URL("http://10.0.2.2:8000/calculate_mean?numbers=$numbersString")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = StringBuilder()
            BufferedReader(InputStreamReader(connection.inputStream)).use {
                var line: String? = it.readLine()
                while (line != null) {
                    response.append(line)
                    line = it.readLine()
                }
            }
            connection.disconnect()

            return response.toString().toDouble()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Double) {
            binding.textViewResult.text = "Mean: $result"
        }
    }
}
