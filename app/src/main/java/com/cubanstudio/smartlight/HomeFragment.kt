package com.cubanstudio.smartlight

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
import com.airbnb.lottie.value.SimpleLottieValueCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket


class HomeFragment : Fragment() {
    companion object IPadress {
        lateinit var address: String
    }

    var first = 0f
    var prog = 0f
    var effects = arrayListOf<String>("SOLID", "PULSING", "TWINKLE", "PHASING", "RAINBOW", "MUSIC")
    var actualeffect = 0

    var on = true

    //@SuppressLint("ClickableViewAccessibility")
    @SuppressLint("ClickableViewAccessibility")
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
        lightBulbColor(animation, Color.argb(1f, .98431372549f, 0.81176470588f, 0.0431372549f)) // NASTAVENI ZAKLADNI BARVY
        animation.setRenderMode(RenderMode.HARDWARE)
        animation.setOnTouchListener { view, event ->
            animation.setMaxFrame(135)
            animation.setMinFrame(90)
            animation.cancelAnimation()
            var screenY = event.getY()
            var viewY = screenY - view.top
            var height = view.height


            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    first = viewY
                    prog = animation.progress

                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    Log.e("PROCENTA", animation.progress.toString())
                    animation.progress = (((first - viewY) / height)) + prog

                    sendMessage("BRIGHT", map((animation.progress * 255).toInt(),170,255,0,255).toString())
                    animation.pauseAnimation()

                    true
                }
            }
            true
        }



        colorslider.addOnChangeListener { slider, value, fromUser ->

            val body = (value * 255).toInt().toString()
            sendMessage("COLOR", (value * 255).toInt().toString())

            slider.thumbColor =
                ColorStateList.valueOf(Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)))
            slider.trackColor =
                ColorStateList.valueOf(Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)))
            lightBulbColor(animation,Color.HSVToColor(255, floatArrayOf(value * 360, 255f, 255f)))
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


        view.findViewById<MaterialButton>(R.id.effectright).setOnClickListener {
            actualeffect++
            if (actualeffect >= effects.size) {
                actualeffect = 0
            }
            sendMessage("EFF", actualeffect.toString())
            // CallAPI().execute("192.168.1.179/effect",actualeffect.toString())
            view.findViewById<TextView>(R.id.effectText).text = effects.get(actualeffect)
            when (actualeffect) {
                0, 1, 2, 5 -> {
                    colorslider.visibility = View.VISIBLE
                }
                else -> {
                    colorslider.visibility = View.INVISIBLE
                }
            }
        }

        view.findViewById<MaterialButton>(R.id.effectleft).setOnClickListener {
            actualeffect--

            if (actualeffect < 0) {
                actualeffect = effects.size - 1
            }
            sendMessage("EFF", actualeffect.toString())
            //CallAPI().execute("192.168.1.179/effect",actualeffect.toString())
            view.findViewById<TextView>(R.id.effectText).text = effects.get(actualeffect)

            when (actualeffect) {
                0, 1, 2, 5 -> {
                    colorslider.visibility = View.VISIBLE
                }
                else -> {
                    colorslider.visibility = View.INVISIBLE
                }
            }
        }

        return view
    }

    private fun sendMessage(head: String, body: String) {


        var thread = Thread(Runnable {


            try {
                //Replace below IP with the IP of that device in which server socket open.
                //If you change port then change the port number in the server side code also.

                var data = "\u0001${head}\u0002${body}\u0003"
                data += "AAAA" + "\u0004\r"
                var s = Socket("192.168.1.179", 9002)
                var out = s.getOutputStream()

                var output = PrintWriter(out)

                output.println(data)
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

    private fun lightBulbColor(anim: LottieAnimationView, color: Int) {
        anim.addValueCallback(
            KeyPath("Bulb background", "**"),
            LottieProperty.COLOR_FILTER,
            SimpleLottieValueCallback<ColorFilter?> {
                PorterDuffColorFilter(
                    color,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )
        anim.addValueCallback(
            KeyPath("Light", "**"),
            LottieProperty.COLOR_FILTER,
            SimpleLottieValueCallback<ColorFilter?> {
                PorterDuffColorFilter(
                    color,
                    PorterDuff.Mode.MULTIPLY
                )
            }
        )
    }

    fun map(
        x: Int,
        in_min: Int,
        in_max: Int,
        out_min: Int,
        out_max: Int
    ): Int{
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }
}


