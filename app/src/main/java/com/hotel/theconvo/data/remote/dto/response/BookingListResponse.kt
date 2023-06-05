package com.hotel.theconvo.data.remote.dto.response

data class BookingListResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: BookingListResponseDescription,
    val timestamp: String
)

data class BookingListResponseDescription(
    val past: List<Any>,
    val upcomming: List<Upcomming>
)

data class Upcomming(
    val address_line_1: String,
    val booking_end_date: String,
    val booking_start_date: String,
    val bookingid: String,
    val comment: Any,
    val current_rating: Any,
    val images: List<Image>,
    val name: String,
    val property_id: String,
    val rating: Any,
    val reservation_id: Int,
    val review_status: Any,
    val reviewid: Any,
    val reviews_count: String,
    val room_id: String,
    val status: String,
    val user_id: String
)

data class Image(
    val image_path: String,
    val thumb: String
)