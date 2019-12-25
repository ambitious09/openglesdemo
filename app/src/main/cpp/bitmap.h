//
// Created by hh l on 2019-12-23.
//

#ifndef OPENGLDEMO_BITMAP_H
#define OPENGLDEMO_BITMAP_H

#import "JniUtil.h"
#import <android/bitmap.h>
#import <string.h>

typedef  uint32_t  ABSize;

typedef  int32_t  ABFormat;

#ifdef ARGB_8888
    typedef uint32_t  APixel;
    ABFormat checkFormat=ANDROID_BITMAP_FORMAT_RGBA_8888;
#elif defined(ARGB_4444)
    typedef uint16_t APixel;
    ABFormat checkFormat = ANDROID_BITMAP_FORMAT_RGBA_4444;
#elif defined(RGB_565)
    typedef uint16_t APixel;
    ABFormat checkFormat = ANDROID_BITMAP_FORMAT_RGB_565;
#elif defined(ALPHA_8)
    typedef uint8_t APixel;
    ABformat checkFormat = ANDROID_BITMAP_FORMAT_A_8;
#else
   typedef uint32_t  APixel;
   ABFormat checkFormat = ANDROID_BITMAP_FORMAT_RGBA_8888;
#endif

class Bitmap{
private:
    APixel  *aPixel;
    JNIEnv *jniEnv;
    _jobject *jbitmap;
    AndroidBitmapInfo androidBitmapInfo;
    int result;
    ABSize  width;
    ABSize  height;
public:
    Bitmap(int with,int height): jniEnv(NULL),jbitmap(NULL){
        aPixel=(APixel *) malloc(sizeof(APixel) * width * height);
        memset(aPixel, 0, width * height);
    }

    Bitmap(JNIEnv *env, jobject bitmap) : aPixel(NULL), jniEnv(env), jbitmap(bitmap) {
        if ((result = AndroidBitmap_getInfo(env, bitmap, &androidBitmapInfo)) < 0) {
            ALOGE("bitmap init failed ! error=%d", result);
            return;
        }
        if (androidBitmapInfo.format != checkFormat) {
            ALOGE("Bitmap format ic_sun not your selection !");
            return;
        }
        if ((result = AndroidBitmap_lockPixels(env, bitmap, (void **) &aPixel)) < 0) {
            ALOGE("bitmap get pixels failed ! error=%d", result);
        }
    }

    ~Bitmap(){
        if(jniEnv){
            AndroidBitmap_unlockPixels(jniEnv,jbitmap);
        } else{
            free(aPixel);
        }
    }

    ABSize  getHeight(){
        return  jniEnv?androidBitmapInfo.height:height;
    }

    ABSize getWidth() {
        return jniEnv ? androidBitmapInfo.width : width;
    }
    ABFormat getType() {
        return checkFormat;
    }
    int getErrorCode() {
        return result;
    }


    operator  APixel *(){
        return  aPixel;
    }

    APixel *operator[](int y) {
        if (y >= getHeight()) return NULL;
        return aPixel + y * getWidth();
    }





};







#endif //OPENGLDEMO_BITMAP_H
