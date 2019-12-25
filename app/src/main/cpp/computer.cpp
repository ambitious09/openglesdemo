//
// Created by hh l on 2019-12-23.
//
#include <jni.h>
#include "computer.h"

static  int count;
extern  "C" JNIEXPORT void JNICALL

//jni-->java
Java_com_example_opengldemo_MainActivity_callBack(JNIEnv *env,jobject activity){
   jclass  activityclass=  env->GetObjectClass(activity);
   jmethodID  jmethodId=env->GetMethodID(activityclass,"setText","(I)V");
   env->CallVoidMethod(activity,jmethodId,count+=1);

}



