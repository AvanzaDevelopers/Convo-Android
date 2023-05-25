package com.hotel.theconvo.data.remote.dto.response

data class HappeningNowResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: ResponseDescription,
    val timestamp: String
)

data class ResponseDescription(
    val data: List<HappeningNowData>
)

data class HappeningNowData(
    val city: String,
    val count: String,
    val country: String,
    val datetime: String,
    val hotel: Int,
    val hotelname: String,
    val id: String,
    val images: List<HappeningNowImage>,
    val isactive: Boolean,
    val isdeleted: Boolean,
    val name: String,
    val videos: List<HappeningNowVideo>
)

data class HappeningNowImage(
    val UUID: String,
    val _id: String,
    val context: String,
    val ext: String,
    val fileDetail: FileDetail,
    val name: String,
    val path: String
)

data class HappeningNowVideo(
    val UUID: String,
    val _id: String,
    val context: String,
    val ext: String,
    val fileDetail: FileDetail,
    val name: String,
    val path: String
)

data class FileDetail(
    val UUID: String,
    val name: String
)