//
// Created by hh l on 2019-12-23.
//

#ifndef OPENGLDEMO_JNIUTIL_H
#define OPENGLDEMO_JNIUTIL_H

#include <jni.h>
#include <cstdlib>
#include <android/log.h>

using  namespace std;
#ifndef LOG_TAG
#define LOG_TAG "NDK-LIB"
#endif
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define ALOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)











#endif //OPENGLDEMO_JNIUTIL_H
