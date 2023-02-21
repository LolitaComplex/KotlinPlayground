package com.doing.androidx.aspectj

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

//@Aspect
class ActivityAspect {

//    @Around("execution(* android.app.Activity.setContentView(..))")
//    fun setContentView(joinPoint: ProceedingJoinPoint) {
//        val signature = joinPoint.signature
//        val className = signature.declaringType.simpleName
//        val methodName = signature.name
//
//        val start = System.currentTimeMillis()
//        joinPoint.proceed()
//        val time = System.currentTimeMillis() - start
//        Log.d("ActivityAspect", "methodSearch:  $className : $methodName time: ${time}ms")
//    }

//    @Around("execution(@com.doing.androidx.aspectj.MethodTrace * *(..))")
//    fun methodTrace(joinPoint: ProceedingJoinPoint) {
//        val signature = joinPoint.signature
//        val className = signature.declaringType.simpleName
//        val methodName = signature.name
//
//        val start = System.currentTimeMillis()
//        joinPoint.proceed()
//        val time = System.currentTimeMillis() - start
//        Log.d("ActivityAspect", "methodTrace: $className : $methodName time: ${time}ms")
//    }
}