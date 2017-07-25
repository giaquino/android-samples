package com.giaquino.sample

fun Any.loadResourceAsString(name: String): String {
  return javaClass.classLoader.getResourceAsStream(name).bufferedReader().readText()
}