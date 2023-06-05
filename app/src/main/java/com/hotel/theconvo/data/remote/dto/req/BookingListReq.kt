package com.hotel.theconvo.data.remote.dto.req

data class BookingListReq(
    val isui: Boolean,
    val page: Page
)

data class Page(
    val currentPageNo: Int,
    val pageSize: Int
)