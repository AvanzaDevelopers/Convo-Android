package com.hotel.theconvo.data.remote.dto.response

data class PropertyDetailsResponse(
    val daysDuration: Int,
    val messageStatus: String,
    val propertyDetails: PropertyDetails,
    val responseCode: Int,
    val responseDescription: String,
    val timestamp: String
)

data class PropertyDetails(
    val TaxAndFeesData: Any,
    val accommodation_type: String,
    val address_line_1: String,
    val address_line_2: String,
    val amenities: List<Amenity>,
    val amount: Int,
    val bookingPolicy: String,
    val cancel_before: Int,
    val check_in_time: Any,
    val check_out_time: Any,
    val city_code: String,
    val contact_email: String,
    val contact_mobile: String,
    val country_code: String,
    val currencySymbol: String,
    val current_rating: Any,
    val description: String,
    val extra_fees: List<ExtraFee>,
    val extras: List<PropertyExtra>,
    val images: List<String>,
    val latitude: Double,
    val longitude: Double,
    val minimum_booking_amount_percentage: Int,
    val minimum_stay: Int,
    val name: String,
    val policies: List<Policy>,
    val property_currency: String,
    val property_id: String,
    val reviews: List<Reviews>,
    val reviews_count: String,
    val rooms: List<Room>,
    val tax_included_in_price: Boolean,
    val tax_percentage: Int
)

data class Amenity(
    val AmenityCategory: String,
    val cost: String,
    val name: String
)

data class ExtraFee(
    val amount: Int,
    val amountType: String,
    val id: Int,
    val name: String
)

data class PropertyExtra(
    val name: String,
    val price: Int,
    val priceType: String
)

data class Policy(

    val description: Any,
    val name: String
)

data class Room(
    val breakFastIncluded: Int,
    val description: String,
    val grandTotal: String,
    val image: String,
    val netAmount: String,
    val nonRefundable: Int,
    val price: Int,
    val roomId: String,
    val roomParameters: List<RoomParameter>,
    val roomRate: Int,
    val roomTotal: String,
    val roomType: String,
    val totalNights: Int,
    val totalRoomRate: String,
    val totalTaxes: String
)

data class RoomParameter(
    val parameter_name: String,
    val parameter_value: String
)

data class Reviews (
    val rating: Int,
    val image: String,
    val profilePic: String,
    val review: String,
    val reviewer: String
    )