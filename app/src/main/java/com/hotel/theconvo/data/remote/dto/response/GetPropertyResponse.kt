package com.hotel.theconvo.data.remote.dto.response

data class GetPropertyResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val searchProperties: SearchProperties,
    val timestamp: String
)

data class SearchProperties(
    val action: String,
    val pageData: PageData,
    val searchKeyword: String,
    val searchResult: List<SearchResult>
)

data class SearchResult(
    val booking_policy: String,
    val number_of_rooms_left: Int,
    val property: Property,
    val property_amenities: List<String>,
    val property_images: List<String>,
    val proximity: Proximity
)

data class Property(
    val address_line_1: String,
    val amount: Double,
    val check_in_time: Any,
    val check_out_time: Any,
    val city_code: String,
    val cloudbedapi: Boolean,
    val contact_email: String,
    val contact_mobile: String,
    val currencySymbol: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val number_of_rooms_left: String,
    val policies: Any,
    val propertyAdditionalPhotos: Any,
    val propertyAmenities: List<String>,
    val propertyCountry: String,
    val propertyCurrency: Any,
    val propertyState: Any,
    val property_amenities: List<PropertyAmenity>,
    val property_id: String,
    val property_images: List<PropertyImage>,
    val property_owner: String,
    val rating: Int,
    val reviews: String,
    val rooms: List<Any>
)

data class Proximity(
    val km: Int,
    val location: String
)


data class PropertyImage(
    val image_path: String,
    val thumb: String
)

data class PropertyAmenity(
    val amenity_category: String,
    val parameter_name: String,
    val parameter_value: String
)

data class PageData(
    val currentPageNo: Int,
    val pageSize: String,
    val totalRecords: Int
)