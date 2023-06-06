package com.hotel.theconvo.data.remote.dto.response

data class BookingListResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: BookingResponseDescription,
    val timestamp: String
)

data class BookingResponseDescription(
    val past: List<Past>,
    val upcomming: List<Upcomming>
)

data class Image(
    val image_path: String,
    val thumb: String
)

data class Upcomming(
    val address_line_1: String,
    val booking_end_date: String,
    val booking_start_date: String,
    val bookingid: String,
    val comment: Any,
    val current_rating: Int,
    val images: List<Image>,
    val name: String,
    val propertyDetail: PropertyDetails,
    val bookingDetail: BookingDetail,
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

data class Past(
    val address_line_1: String,
    val booking_end_date: String,
    val booking_start_date: String,
    val bookingid: String,
    val comment: Any,
    val current_rating: Any,
    val images: List<Image>,
    val name: String,
    val propertyDetail: PropertyDetails,
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

/**data class BookingListDetail(
    val bookingDetail: BookingDetail
)*/

data class BookingDetail(
   // val addons: Addons,
    val adults: String,
    val amount_details: AmountDetails,
    val amount_paid: String,
    val balance_amount: Any,
    val booking_end_date: String,
    val booking_start_date: String,
    val cancel_response: Any,
    val cancellationcomment: Any,
    val cancellationdate: Any,
    val cancellationreason: Any,
    val cancelledby: Any,
    val check_in: Any,
    val check_out: Any,
    val children: String,
    val country: String,
    val created_at: String,
    val created_by: String,
    val email: String,
    val first_name: String,
    val id: String,
    val is_registered: Boolean,
    val last_name: String,
    val loyalty_points_equivalent_amount: Any,
    val notes: Any,
    val payment_date: Any,
    val payment_mode: String,
    val payment_reference_no: Any,
    val phone_no: String,
    val pre_payment_amount: Any,
    val property_id: String,
    val property_owner: Any,
    val property_room_type_id: String,
    val reservation_id: String,
    val response: Any,
    val room_no: Any,
    val room_variation_id: String,
    val rooms_count: String,
    val rooms_rate_id: String,
    val status: String,
    val user_card_id: Any,
    val user_id: String
)

data class AmountDetails(
    val VATAmount: String,
    val accTax: String,
    val breakFastCharges: String,
    val breakfastTax: String,
    val cleaningTax: String,
    val netAmount: String,
    //val roomRateDetailed: RoomRateDetailed,
    val roomTotal: String,
    val serviceTax: String,
    val totalRoomRate: String,
    val totalTaxes: String
)