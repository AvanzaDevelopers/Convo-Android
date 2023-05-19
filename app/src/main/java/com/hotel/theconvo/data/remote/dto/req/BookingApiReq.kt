package com.hotel.theconvo.data.remote.dto.req

data class BookingApiReq(
    val adults: String,
    val amount_paid: String,
    val booking_end_date: String,
    val booking_policy: String,
    val booking_start_date: String,
    val children: Int,
    val country: String,
    val email: String,
    val firstName: String,
    val inventory_count: String,
    val lastName: String,
    val payment_mode: String,
    val phone_no: String,
    val property_id: String,
    val property_room_type_id: String,
    val quantity: String,
    val roomTypeName: String,
    val room_variation_id: String
)