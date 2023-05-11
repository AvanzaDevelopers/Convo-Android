package com.hotel.theconvo.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


object AESUtils {

    val SECRET_KEY = "b75524255a7f54d2726a951bb39204df"
    val SECRET_IV = "1583288699248111"



    @Throws(Exception::class)
    fun generateAESKey(seedString: String): String {
        val keyGenerator = KeyGenerator.getInstance("AES")
        val secureRandom = SecureRandom.getInstance("SHA1PRNG")
        secureRandom.setSeed(seedString.toByteArray())
        keyGenerator.init(128, secureRandom)
        val secretKey: SecretKey = keyGenerator.generateKey()
        return Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    }




}