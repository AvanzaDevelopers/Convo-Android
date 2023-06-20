package com.hotel.theconvo.util

object AllKeys {

     var token: String = "token"

     var email: String = "email"

     var firstName: String = "firstName"

     var lastName: String = "lastName"

     var mobileNumber: String = "mobileNumber"

     var countryResidence: String  ="countryResidence"

     fun validatePassword(password: String): Boolean {

          /**Later on use this commented logic */
         // val pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])(?=\\S+\$).{8,}\$".toRegex()
         // return password.matches(pattern)

          if(password.length<8) {
               return false
          }
          else {
             return  true
          }

     }


}