package com.cubanstudio.smartlight

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.model.content.GradientColor
import com.airbnb.lottie.value.SimpleLottieValueCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket


class HomeFragment : Fragment() {
    companion object IPadress {
        lateinit var address: String
    }

    var effects = arrayListOf<String>("SOLID", "PULSING", "TWINKLE","PHASING", "RAINBOW", "MUSIC")
    var actualeffect = 0

    var on = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.home_fragment, container, false)

        var animation =
            view.findViewById<LottieAnimationView>(R.id.animation_view)
        var colorslider = view.findViewById<Slider>(R.id.H)
        animation.speed = 1.5f
        animation.setMaxFrame(45)
        animation.addValueCallback(
            KeyPath("Bulb background", "**"),
            LottieProperty.COLOR_FILTER,
            SimpleLottieValueCallback<ColorFilter?> {
                PorterDuffColorFilter(
                    Color.argb(1f, .98431372549f, 0.81176470588f, 0.0431372549f),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )
        animation.addValueCallback(
            KeyPath("Light", "**"),
            LottieProperty.COLOR_FILTER,
            SimpleLottieValueCallback<ColorFilter?> {
                PorterDuffColorFilter(
                    Color.argb(1f, .98431372549f, 0.81176470588f, 0.0431372549f),
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )
        animation.setRenderMode(RenderMode.HARDWARE)
        animation.setOnClickListener {
            if (on) {
                val a = AlphaAnimation(1f, 0f)
                a.duration = 1200
                a.repeatCount = 0
                colorslider.startAnimation(a)
                colorslider.visibility = View.INVISIBLE
                animation.setMinFrame(45)
                animation.setMaxFrame(90)
                animation.playAnimation()
                val head = "STATE"
                val body = "0"
                var data = "\u0001${head}\u0002${body}\u0003"
                data += "AAAA" + "\u0004\r"
                sendMessage(data)
                on = false
            } else {
                val a = AlphaAnimation(0f, 1f)
                colorslider.visibility = View.VISIBLE
                a.duration = 1200
                a.repeatCount = 0
                colorslider.startAnimation(a)
                animation.setMinFrame(90)
                animation.setMaxFrame(135)
                animation.playAnimation()
                val head = "STATE"
                val body = "1"
                var data = "\u0001${head}\u0002${body}\u0003"
                data += "AAAA" + "\u0004\r"
                sendMessage(data)
                on = true
            }
        }

        colorslider.addOnChangeListener { slider, value, fromUser ->
            val head = "COLOR"
            val body = (value * 255).toInt().toString()
            var data = "\u0001${head}\u0002${body}\u0003"
            data += "AAAA" + "\u0004\r"

            sendMessage(data)

            slider.thumbColor =
                ColorStateList.valueOf(Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)))
            slider.trackColor =
                ColorStateList.valueOf(Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)))
            animation.addValueCallback(
                KeyPath("Bulb background", "**"),
                LottieProperty.COLOR_FILTER,
                SimpleLottieValueCallback<ColorFilter?> {
                    PorterDuffColorFilter(
                        Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)),
                        PorterDuff.Mode.MULTIPLY
                    )
                }
            )
            animation.addValueCallback(
                KeyPath("Light", "**"),
                LottieProperty.COLOR_FILTER,
                SimpleLottieValueCallback<ColorFilter?> {
                    PorterDuffColorFilter(
                        Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)),
                        PorterDuff.Mode.MULTIPLY
                    )
                }
            )
            animation.refreshDrawableState()
        }



        view.findViewById<MaterialButton>(R.id.adddevice).setOnClickListener {
            val anim1 = TranslateAnimation(0f, 0f, 0f, 200f)
            anim1.duration = 500
            val anim2 = AlphaAnimation(1f, 0f)
            anim2.duration = 350
            val anim3 = AnimationSet(false)
            anim3.addAnimation(anim1)
            anim3.addAnimation(anim2)
            val navBar =
                activity?.findViewById<BottomNavigationView>(R.id.navbar)
            navBar?.startAnimation(anim3)
            navBar?.visibility = View.INVISIBLE

            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.contain, DeviceListFragment())
            ft?.addToBackStack("Main")
            ft?.commit()

        }
        view.findViewById<MaterialButton>(R.id.POST).setOnClickListener {

            var api = CallAPI()
            api.execute(address, "", "")
        }

        view.findViewById<MaterialButton>(R.id.effectright).setOnClickListener {
            actualeffect++
            if (actualeffect >= effects.size) {
                actualeffect = 0
            }
            CallAPI().execute("192.168.1.179/effect",actualeffect.toString())
            view.findViewById<TextView>(R.id.effectText).text = effects.get(actualeffect)
            when (actualeffect) {
                0,1,2,5 ->{
                    colorslider.visibility = View.VISIBLE
                }
                else ->{
                    colorslider.visibility = View.INVISIBLE
                }
            }
        }

        view.findViewById<MaterialButton>(R.id.effectleft).setOnClickListener {
            actualeffect--

            if (actualeffect < 0) {
                actualeffect = effects.size - 1
            }
            CallAPI().execute("192.168.1.179/effect",actualeffect.toString())
            view.findViewById<TextView>(R.id.effectText).text = effects.get(actualeffect)

            when (actualeffect) {
                0,1,2,5 ->{
                    colorslider.visibility = View.VISIBLE
                }
                else ->{
                    colorslider.visibility = View.INVISIBLE
                }
            }
        }

        return view
    }

    private fun sendMessage(msg: String) {


        var thread = Thread(Runnable {


            try {
                //Replace below IP with the IP of that device in which server socket open.
                //If you change port then change the port number in the server side code also.
                var s = Socket("192.168.1.179", 9002)
                var out = s.getOutputStream()

                var output = PrintWriter(out)

                output.println(msg)
                output.flush()





                output.close()
                out.close()
                s.close()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (x: Exception) {
                x.printStackTrace()
            }

        })

        thread.start()
    }
}


