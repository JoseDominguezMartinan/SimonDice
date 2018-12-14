package com.example.jose.simondice

import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : Activity() {
    // variables botones declaradas aqui para poder ser utilizados desde metodos, pero inicializados dentro del oncreate para llamar
    // primero al setcontentview
    lateinit var botonRojo: Button
    lateinit var botonVerde: Button
    lateinit var botonAzul: Button
    lateinit var botonAmarillo: Button
    lateinit var botonRestart: Button

    lateinit var click: MediaPlayer
    lateinit var fallo: MediaPlayer
    lateinit var inicio: MediaPlayer

    var boton=0 // almacenaremos un entero para determinar el boton que debe ser pulsado en la secuencia

    var botonPulsado = Random() // numero random para que la secuencia de numeros pulse uno de los botones

    val handler = Handler() // objeto handler para realizar el delay que permitira la secuencia
    var vueltas = 1 // variable que indica el numero de botones que seran pulsados en la secuencia

    var secuencia: ArrayList<Int> = ArrayList() // array donde guardaremos la secuencia de botones
    var numSecuencia=0 // para controlar la comparacion de la tecla que pulsamos igualarla a la posicion del array
    var perdido=false



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // variables donde vamos a recoger los botones del xml
         botonRojo = findViewById<Button>(R.id.rojo)
         botonVerde = findViewById<Button>(R.id.verde)
         botonAmarillo = findViewById<Button>(R.id.amarillo)
         botonAzul = findViewById<Button>(R.id.azul)
         botonRestart = findViewById<Button>(R.id.restart)


            // marcamos la orientacion de la pantalla, no se gira al girar el dispositivo
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        // acciones que realizara cada uno de los botones cuando los pulsamos
            botonRojo.setOnClickListener{
                click = MediaPlayer.create(this, R.raw.click)
                sonido(click)
                comprobar(0) // llamada al metodo que comprueba con la secuencia indicando el boton que es
                cambiarColorRojo(botonRojo) // hacer el efecto de cambio de color
                handler.postDelayed({colorOriginalRojo(botonRojo)},200)
            }
        botonAzul.setOnClickListener{
            click = MediaPlayer.create(this, R.raw.click)
            sonido(click)
            comprobar(2)
            cambiarColorAzul(botonAzul)
            handler.postDelayed({colorOriginalAzul(botonAzul)},200)
        }
        botonVerde.setOnClickListener {
            click = MediaPlayer.create(this, R.raw.click)
            sonido(click)
            comprobar(1)
            cambiarColorVerde(botonVerde)
            handler.postDelayed({ colorOriginalVerde(botonVerde) }, 200)
        }
        botonAmarillo.setOnClickListener{
            click = MediaPlayer.create(this, R.raw.click)
            sonido(click)
            comprobar(3)
            cambiarColorAmarillo(botonAmarillo)
            handler.postDelayed({colorOriginalAmarillo(botonAmarillo)},200)
        }

        botonRestart.setOnClickListener {
            inicio = MediaPlayer.create(this, R.raw.inicio)
            sonido(inicio)
            botonRestart.setText("Reiniciar")
            numSecuencia=0
            vueltas=1
            secuencia.clear()
            secuencia()

        }
        bloquearBotones()

    }

    /** metodo para cambiar el color del boton a uno mas oscuro cuando pulsamos el boton

     */
    private fun cambiarColorRojo(botonRojo: Button) {
        botonRojo.setBackgroundColor(getResources().getColor(R.color.rojo_oscuro))

    }

    /** metodo para restablecer el color original cuando soltamos el boton
     *
     */
    private fun colorOriginalRojo(botonRojo: Button) {
        botonRojo.setBackgroundColor(getResources().getColor(R.color.rojo))
    }

    private fun cambiarColorAmarillo(botonAmarillo: Button) {
        botonAmarillo.setBackgroundColor(getResources().getColor(R.color.amarillo_oscuro))

    }

    private fun colorOriginalAmarillo(botonAmarillo: Button) {
        botonAmarillo.setBackgroundColor(getResources().getColor(R.color.amarillo))
    }

    private fun cambiarColorAzul(botonAzul: Button) {
        botonAzul.setBackgroundColor(getResources().getColor(R.color.azul_oscuro))

    }

    private fun colorOriginalAzul(botonAzul: Button) {
        botonAzul.setBackgroundColor(getResources().getColor(R.color.blue))
    }

    private fun cambiarColorVerde(botonVerde: Button) {
        botonVerde.setBackgroundColor(getResources().getColor(R.color.verde_oscuro))

    }

    private fun colorOriginalVerde(botonVerde: Button) {
        botonVerde.setBackgroundColor(getResources().getColor(R.color.verde))
    }


    /**
     * metodo que realizara la secuencia de colores cuando sea el turno de la maquina
      */

    private fun secuencia() {
        bloquearBotones()
        botonRestart.isClickable=false
        textEmpezar.text = "        COPIAME SI PUEDES"

        var retraso=1000L // variable donde meteremos el retraso entre boton y boton , se tiene que ir incrementando para qe no se pulsen todos juntos

            boton = botonPulsado.nextInt(3) // obtenemos un numero entero entre 0 y 3, de forma aleatoria
            secuencia.add(boton)
            for(numBoton in secuencia) {

                when (numBoton) { // cuando el boton es una de las opciones marcadas a continuacion

                    0 -> {
                        handler.postDelayed({
                            // hacemos el efecto de los colores posponiendo el tiempo indicado
                            cambiarColorRojo(botonRojo)
                            handler.postDelayed({
                                colorOriginalRojo(botonRojo)
                            }, 250)
                        }, retraso)


                    }
                    1 -> {
                        handler.postDelayed({
                            cambiarColorVerde(botonVerde)
                            handler.postDelayed({
                                colorOriginalVerde(botonVerde)
                            }, 250)
                        }, retraso)
                    }
                    2 -> {
                        handler.postDelayed({
                            cambiarColorAzul(botonAzul)
                            handler.postDelayed({
                                colorOriginalAzul(botonAzul)
                            }, 250)
                        }, retraso)
                    }
                    3 -> {
                        handler.postDelayed({
                            cambiarColorAmarillo(botonAmarillo)
                            handler.postDelayed({
                                colorOriginalAmarillo(botonAmarillo)
                            }, 250)
                        }, retraso)
                    }

                }
                retraso += 600 // aumentamos el tiempo entre boton y boton para que no se marquen todas a la vez
            }

        handler.postDelayed({

            textEmpezar.text = "        ES TU TURNO"
            desbloquearBotones()
            botonRestart.isClickable=true
        },retraso)

    }

    /**
     * metodo para comprobar la secuencia numerica con los botones pulsados por el jugador
     */

    private fun comprobar(boton:Int) {


            if (numSecuencia < vueltas) { // si el numero de botones pulsados es menor que el numero de botones de la secuencia


                if (secuencia.get(numSecuencia) == boton) { // si el boton coincide lo indicamos


                }
                else { // en caso contrario indicamos el error
                    Toast.makeText(this,"¡ERROR!",Toast.LENGTH_LONG).show()
                    perdido=true



                }

                numSecuencia=numSecuencia+1 // aumentamos en uno el numero de la secuencia

            }



        if(numSecuencia==vueltas && perdido==false) {
            Toast.makeText(this,"¡CORRECTO!",Toast.LENGTH_LONG).show()
            bloquearBotones()
            numSecuencia = 0 // en caso de que finalizara la secuencia lo marcamos como cero

            vueltas++
            secuencia()
        }
        if(perdido==true){
            bloquearBotones()
            fallo = MediaPlayer.create(this, R.raw.fallo)
            sonido(fallo)
            textEmpezar.text = "        HAS PERDIDO"
            perdido=false

            Toast.makeText(this,"REINICIA EL JUEGO PARA SEGUIR JUGANDO",Toast.LENGTH_LONG).show()

        }

    }

    /**
     * metodo para prohibir pulsar los botones cuando deseemos
     */
    private fun bloquearBotones(){
        botonAmarillo.isClickable=false
        botonAzul.isClickable=false
        botonVerde.isClickable=false
        botonRojo.isClickable=false

    }

    /**
     * metodo para activar de nuevo los botones
     */
    private fun desbloquearBotones(){
        botonAmarillo.isClickable=true
        botonAzul.isClickable=true
        botonVerde.isClickable=true
        botonRojo.isClickable=true

    }
    private fun sonido(sonidos: MediaPlayer) {
        sonidos.start()

    }



}
