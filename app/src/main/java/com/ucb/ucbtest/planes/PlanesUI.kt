package com.ucb.ucbtest.planes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.model.LatLng
import androidx.lifecycle.*
import androidx.lifecycle.compose.LocalLifecycleOwner

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
@Composable
fun PlanesScreen(
    viewModel: PlanesViewModel = hiltViewModel()
) {
    val planes by viewModel.planes.collectAsState()
    val indiceActual by viewModel.indiceActual.collectAsState()
    val context = LocalContext.current
    var mostrarFormulario by remember { mutableStateOf(false) }
    var telefonoReferencia by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf("") }
    var longitud by remember { mutableStateOf("") }
    val backgroundColor = Color(0xFF0D47A1)
    val accentColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Nuestros planes móviles",
                color = accentColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "La mejor cobertura, increíbles beneficios y un compromiso con el planeta",
                color = accentColor.copy(alpha = 0.9f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Caracteristica("Llamadas y sms ilimitados")
                Caracteristica("Hotspot, comparte tus datos")
                Caracteristica("Redes sociales ilimitadas incluidas")
                Caracteristica("Arma tu plan con más apps ilimitadas")
                Caracteristica("CO₂ negativo")
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (planes.isNotEmpty()) {
                val plan = planes[indiceActual]

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = plan.nombre, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Precio: ${plan.precio} Bs", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "GB: ${plan.gb} GB", style = MaterialTheme.typography.bodyLarge)

                        if (plan.precioOriginal > plan.precio) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Antes: ${plan.precioOriginal} Bs",
                                style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough),
                                color = Color.Gray
                            )
                        }
                        Button(
                            onClick = { val mensaje = "Hola, UCB mobile, preciso su ayuda. Estoy interesado en el plan: ${plan.nombre}, de ${plan.precio} Bs con ${plan.gb} GB."
                                enviarMensajeWhatsapp(context, mensaje) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Quiero este plan")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.irIzquierda() }) {
                        Text("← Anterior")
                    }

                    Button(onClick = { viewModel.irDerecha() }) {
                        Text("Siguiente →")
                    }
                }
            } else {
                CircularProgressIndicator(color = Color.White)
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { mostrarFormulario = !mostrarFormulario },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (mostrarFormulario) "Ocultar formulario de envío" else "Solicitar envío de SIM")
            }

            if (mostrarFormulario) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = telefonoReferencia,
                    onValueChange = { telefonoReferencia = it },
                    label = { Text("Teléfono de referencia") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Selecciona tu ubicación en el mapa:", color = Color.White)

                MapaSeleccion { lat, lng ->
                    latitud = lat.toString()
                    longitud = lng.toString()
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Latitud: $latitud", color = Color.White)
                Text(text = "Longitud: $longitud", color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val mensaje = """
                            Hola, UCB mobile, preciso su ayuda.
                            Por favor enviar SIM al teléfono de referencia: $telefonoReferencia.
                            Ubicación: ($latitud, $longitud).
                        """.trimIndent()
                        enviarMensajeWhatsapp(context, mensaje)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar")
                }
            }
        }
    }
}

@Composable
fun Caracteristica(texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check",
            tint = Color.Green,
            modifier = Modifier
                .size(20.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

fun enviarMensajeWhatsapp(context: Context, mensaje: String) {
    val numero = "59170000000" // <-- Cambia esto por el número de UCB Mobile con código de país
    //val mensaje = "Hola, UCB mobile, preciso su ayuda. Lo puedes agregar por favor?"
    val uri = Uri.parse("https://wa.me/$numero?text=${Uri.encode(mensaje)}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

@Composable
fun MapaSeleccion(onCoordenadasSeleccionadas: (Double, Double) -> Unit) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    var selectedPosition by remember { mutableStateOf<LatLng?>(null) }

    AndroidView({ mapView }) { view ->
        view.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                selectedPosition = latLng
                onCoordenadasSeleccionadas(latLng.latitude, latLng.longitude)
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación seleccionada"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context).apply { onCreate(Bundle()) } }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    return mapView
}