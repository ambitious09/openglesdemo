//
// Created by hh l on 2019-12-23.
//
#define LOG_TAG "bitmap"
#define ARGB_4444
#include  "bitmap.h"

#include <android/bitmap.h>


extern "C" JNIEXPORT void JNICALL


Java_com_example_opengldemo_NBitmapLib_renderGray(JNIEnv *jniEnv, jobject obj, jobject bitmap) {
    Bitmap bm(jniEnv, bitmap);
    ABSize height = bm.getHeight();
    ABSize width = bm.getWidth();
    const APixel MODEL = 0xF;
    APixel color;
    APixel av;
    APixel *pixelArray = bm;
    ABSize length = height * width;
    for (ABSize i = 0; i < length; i++) {
        av = 0;
        color = pixelArray[i];
        av += (color >>= 4) & MODEL;
        av += (color >>= 4) & MODEL;
        av += (color >> 4) & MODEL;
        av /= 3;
        color = av;
        color = (color << 4) + av;
        color = (color << 4) + av;
        pixelArray[i] = color << 4;
    }

}
extern "C" JNIEXPORT jintArray JNICALL
Java_com_example_opengldemo_NBitmapLib_getImgToGray(JNIEnv *env, jobject instance, jintArray data_,
                                                    jint w,
                                                    jint h) {
    jint *data = env->GetIntArrayElements(data_, NULL);

// TODO
    if (data == NULL) {
        return 0; /* exception occurred */
    }
    int alpha = 0xFF << 24;
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
// 获得像素的颜色
            int color = data[w * i + j];
            int red = ((color & 0x00FF0000) >> 16);
            int green = ((color & 0x0000FF00) >> 8);
            int blue = color & 0x000000FF;

            color = (red + green + blue) / 3;

            color = alpha | (color << 16) | (color << 8) | color;

            data[w * i + j] = color;
        }
    }

    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, data);
    env->SetIntArrayRegion(result, 0, size, data);
    env->ReleaseIntArrayElements(data_, data, 0);
    return result;
}
#undef ARGB_4444