package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */
data class H264(
    @SerializedName("profile_level")
    var profileLevel: String?,
    @SerializedName("video_width")
    var videoWidth: Int,
    @SerializedName("video_height")
    var videoHeight: Int,
    @SerializedName("cam_fps")
    var videoFPS: Int,
    @SerializedName("video_min_bitrate")
    var videoMinBitrate: Int,
    @SerializedName("video_bitrate")
    var videoBitrate: Int,
    @SerializedName("video_max_bitrate")
    var videoMaxBitrate: Int,
    @SerializedName("game_width")
    var gameWidth: Int,
    @SerializedName("game_height")
    var gameHeight: Int,
    @SerializedName("game_fps")
    var gameFPS: Int,
    @SerializedName("game_bitrate")
    var gameBitrate: Int
) {
    companion object {
        fun default(): H264 =
            H264(
                "AVCProfileHigh",
                1280,
                720,
                30,
                1000 * 1000,
                1000 * 1000,
                1500 * 1000,
                544,
                960,
                30,
                1200 * 1000
            )
    }
}
