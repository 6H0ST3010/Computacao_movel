package vector

import kotlin.math.*

data class Vec2(val x: Double, val y: Double): Comparable<Vec2>{
    operator fun plus(index: Vec2): Vec2 {
        return Vec2(x.plus(index.x), y + index.y)
    }

    operator fun minus(index: Vec2): Vec2 {
        return Vec2(x.minus(index.x), y - index.y)
    }

    operator fun times(index: Double): Vec2 {
        return Vec2(x * index, y * index)
    }

    operator fun unaryMinus(): Vec2 {
        return Vec2(-x, -y)
    }

    override operator fun compareTo(other: Vec2): Int {
        return magnitude().compareTo(other.magnitude())
    }

    fun magnitude(): Double{
        return hypot(x, y) // raiz(x^2 + y^2)
    }

    fun dot(index: Vec2): Double {
        return x * index.x + y * index.y
    }

    fun normalized(): Vec2{
        val mag = magnitude()
        if (mag == 0.0){
            throw IllegalStateException("Cant normalize the zero vector (0, 0)")
        }
        return Vec2(x / mag, y / mag) // x / magnitude
    }

    operator fun get(index: Int): Double{
        return when(index) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("Vec2 has only indices 0 and 1")
        }
    }

    /* conflict with the class
    operator fun component1(): Double{
        return x
    }
    operator fun componente2(): Double {
        return y
    }*/
}

operator fun Double.times(v: Vec2): Vec2{
    return Vec2(this * v.x, this * v.y)
}

fun main(){
    val a = Vec2 (3.0 , 4.0)
    val b = Vec2 (1.0 , 2.0)
    println ("a = $a") // a = Vec2 (x=3.0 , y =4.0)
    println ("b = $b") // b = Vec2 (x=1.0 , y =2.0)
    println ("a + b = ${a + b}") // a + b = Vec2 (x=4.0 , y =6.0)
    println ("a - b = ${a - b}") // a - b = Vec2 (x=2.0 , y =2.0)
    println ("a * 2.0 = ${a * 2.0} ") // a * 2.0 = Vec2 (x=6.0 , y =8.0)
    println ("-a = ${ -a}") // -a = Vec2 (x= -3.0 , y= -4.0)
    println ("|a| = ${a. magnitude ()}") // |a| = 5.0
    println ("a dot b = ${a.dot(b)}") // a dot b = 11.0
    println (" norm (a) = ${a. normalized ()}")
    // norm (a) = Vec2 (x=0.6 , y =0.8)
    println ("a[0] = ${a [0]} ") // a[0] = 3.0
    println ("a[1] = ${a [1]} ") // a[1] = 4.0
    println ("a > b = ${a > b}") // a > b = true
    println ("a < b = ${a < b}") // a < b = false
    val vectors = listOf ( Vec2 (1.0 , 0.0) , Vec2 (3.0 , 4.0) , Vec2 (0.0 , 2.0) )
    println (" Longest = ${vectors.max()}") // Longest = Vec2 (x=3.0 ,y =4.0)
    println (" Shortest = ${vectors.min()}") // Shortest = Vec2 (x=1.0 ,y =0.0)

    println("2.0 * a = ${2.0 * a}") // 2.0 * a = Vec2 (x=6.0 , y =8.0)
    val (x, y) = b
    println("x = $x") // x = 1.0
    println("y = $y") //y = 2.0
}