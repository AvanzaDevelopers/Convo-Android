package com.hotel.theconvo.data.remote.dto.req

data class PropertyDetailsReq(
    val searchCriteria: PropertyDetailsSearchCriteria
)

data class PropertyDetailsSearchCriteria(
    val end_date: String,
    val property_id: String,
    val required_room: RequiredRoom,
    val start_date: String
)

data class RequiredRoom(
    val adults: String,
    val childrens: String,
    val rooms: String
)

