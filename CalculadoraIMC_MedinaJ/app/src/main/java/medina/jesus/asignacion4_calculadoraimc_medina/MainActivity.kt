package medina.jesus.asignacion4_calculadoraimc_medina

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var etPeso: EditText
    private lateinit var etEstatura: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView
    private lateinit var tvCategoria: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicialización de las vistas
        etPeso = findViewById(R.id.etPeso)
        etEstatura = findViewById(R.id.etEstatura)
        btnCalcular = findViewById(R.id.btnCalcular)
        tvResultado = findViewById(R.id.tvResultado)
        tvCategoria = findViewById(R.id.tvCategoria)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Evento botón click o clickListener
        btnCalcular.setOnClickListener {
            calcularIMC()
        }
    }

    // Función para calcular el IMC
    private fun calcularIMC() {
        // Obtener el peso y la estatura desde los EditText
        val pesoStr = etPeso.text.toString()
        val estaturaStr = etEstatura.text.toString()

        // Verificar que los campos no estén vacíos
        if (pesoStr.isNotEmpty() && estaturaStr.isNotEmpty()) {
            // Convertir a valores numéricos
            val peso = pesoStr.toFloat()
            val estatura = estaturaStr.toFloat() / 100 // Convertir cm a metros

            // Calcular el IMC
            val imc = peso / (estatura * estatura)

            // Mostrar el resultado en el TextView
            tvResultado.text = "Tu IMC es: %.2f".format(imc)

            // Determinar la categoría del IMC y el color correspondiente
            val (categoria, colorResId) = when {
                imc < 18.5 -> "Bajo peso" to R.color.colorGreenish
                imc < 24.9 -> "Normal" to R.color.colorGreen
                imc < 29.9 -> "Sobrepeso" to R.color.colorYellow
                imc < 34.9 -> "Obesidad grado 1" to R.color.colorOrange
                imc < 39.9 -> "Obesidad grado 2" to R.color.colorRed
                else -> "Obesidad grado 3" to R.color.colorBrown
            }

            // Mostrar la categoría en el TextView
            tvCategoria.text = "Categoría: $categoria"

            // Cambiar el color del texto de la categoría según el IMC
            tvCategoria.setTextColor(ContextCompat.getColor(this, colorResId))
        } else {
            // Mostrar un mensaje de error si los campos están vacíos
            tvResultado.text = "Por favor, ingresa tu peso y estatura."
            tvCategoria.text = ""
        }
    }
}