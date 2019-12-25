package com.example.opengldemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.graphics.drawable.toBitmap


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()



    }

    private fun initView() {
//        val java_bp:Bitmap=resources.getDrawable(R.drawable.ic_sun).toBitmap() as Bitmap
////         val java_bp=     bitmap1.copy(Bitmap.Config.ARGB_8888,true)
//        val w=java_bp.width
//        val h=java_bp.height
//        val pix=IntArray(w*h)
//        java_bp.setPixels(pix,0,w,0,0,w,h)
//        val result=NBitmapLib.getImgToGray(pix,w,h)
//        val resultbp=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_4444)
//        resultbp.setPixels(result,0,w,0,0,w,h)
//        iv.setImageBitmap(resultbp)




        val bitmap:Bitmap=BitmapFactory.decodeResource(resources,R.drawable.ic_sun)
        val jni_bitmap=bitmap.copy(Bitmap.Config.ARGB_4444,true)
        var startTime = System.currentTimeMillis()
        NBitmapLib.renderGray(jni_bitmap)
        Log.e("---time----", "JNI Time:${System.currentTimeMillis() - startTime}")
        mnativeiv.setImageBitmap(jni_bitmap)



        val java_bitmap=bitmap.copy(Bitmap.Config.ARGB_4444,true)
        startTime = System.currentTimeMillis()
        NBitmapLib.javaRenderGray(java_bitmap)
        //kotlinRenderGray(java_bitmap)
        Log.e("---time----", "Java Time:${System.currentTimeMillis() - startTime}")
        mjavaiv.setImageBitmap(java_bitmap)


    }

    private fun initData() {
        sample_text.text = stringFromJNI()

        sample_text.setOnClickListener {
            callBack()
        }
    }

    fun  setText(count: Int){
        result_text.text="$count"
    }



    external  fun callBack()

    external fun stringFromJNI(): String


}
