package com.hotel.theconvo.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.KeyGenerator


object AESUtils {

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