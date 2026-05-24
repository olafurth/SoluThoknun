package com.example.soluthoknum

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.soluthoknum.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReikna.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        val verdText = binding.etVerd.text.toString().trim()
        val soluThoknunText = binding.etSoluThoknum.text.toString().trim()
        val minHlutfallText = binding.etMinHlutfall.text.toString().trim()

        if (verdText.isEmpty() || soluThoknunText.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val verd = verdText.toDoubleOrNull()
        val soluThoknum = soluThoknunText.toDoubleOrNull()
        val minHlutfall = if (minHlutfallText.isEmpty()) 70.0 else minHlutfallText.toDoubleOrNull()

        if (verd == null || soluThoknum == null || minHlutfall == null) {
            Toast.makeText(this, getString(R.string.error_invalid_number), Toast.LENGTH_SHORT).show()
            return
        }

        if (minHlutfall < 0.0 || minHlutfall > 100.0) {
            Toast.makeText(this, getString(R.string.error_invalid_hlutfall), Toast.LENGTH_SHORT).show()
            return
        }

        val result = verd * (soluThoknum / 100.0) * (minHlutfall / 100.0)
        val resultMedVsk = result * 1.24

        val formatter = NumberFormat.getNumberInstance(Locale("is", "IS"))
        formatter.maximumFractionDigits = 0
        val formattedResult = formatter.format(result)
        val formattedVerd = formatter.format(verd)
        val formattedResultMedVsk = formatter.format(resultMedVsk)

        val hlutfallDisplay = if (minHlutfall == minHlutfall.toLong().toDouble())
            minHlutfall.toLong().toString() else minHlutfall.toString()

        binding.tvResult.text = getString(R.string.result_format, formattedResult)
        binding.tvResultDetail.text = getString(
            R.string.result_detail_format,
            formattedVerd,
            soluThoknum,
            hlutfallDisplay,
            formattedResult
        )
        binding.tvResultVsk.text = getString(R.string.result_format_vsk, formattedResultMedVsk)
        binding.resultCard.visibility = android.view.View.VISIBLE
    }
}
