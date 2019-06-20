package com.vng.live.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vng.live.BuildConfig
import java.util.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 24/05/2019
 */

data class ConfigsData(
    @SerializedName("api_url")
    var apiUrl: String? = null,
    @SerializedName("share_stream_url")
    var shareStreamUrl: String? = null,
    @SerializedName("share_video_url")
    var shareVideoUrl: String? = null,
    @SerializedName("comm_server")
    var commServer: List<String>? = null,
    @SerializedName("streaming_server")
    var streamingServer: List<String>? = null,
    @SerializedName("comm_server_retries")
    var commServerRetries: Int = 0,
    @SerializedName("comm_server_ping_time")
    var commServerPingTime: Int = 0,
    @SerializedName("comm_server_retry_timeout")
    var commServerRetryTimeout: Int = 0,
    @SerializedName("video_width")
    var videoWidth: Int = 0,
    @SerializedName("video_height")
    var videoHeight: Int = 0,
    @SerializedName("video_fps")
    var videoFps: Int = 0,
    @SerializedName("video_bitrate")
    var videoBitrate: Int = 0,
    @SerializedName("game_bitrate")
    var gameBitrate: Int = 0,
    @SerializedName("player")
    var player: Int = 0,
    @SerializedName("payment_disable")
    var paymentDisable: Int = 0,
    @SerializedName("update_app")
    var updateApp: Int = 0,
    @SerializedName("streamer_mode")
    var streamerMode: Int = 1,
    @SerializedName("private_chat_disable")
    var privateChatDisable: Int = 0,
    @SerializedName("theme")
    var theme: Int = 1,
    @SerializedName("qr_login_url")
    var qrLoginUrl: String? = "",
    @SerializedName("comm_udp_server")
    var udpServerList: List<NetAddress>? = null,
    @SerializedName("comm_tcp_server")
    var tcpServerList: List<NetAddress>? = null,
    @SerializedName("h264")
    var h264: H264? = null,
    @SerializedName("buy_vip_url")
    var buyVipUrl: String? = null,
    @SerializedName("firstPrizeInfo")
    var firstPrizeInfo: FirstPrizeInfo? = null,
    @SerializedName("greetings")
    var greetings: List<Greeting>? = null,
    @SerializedName("admin")
    var admin: Admin? = null,
    @SerializedName("fanpage")
    var fanpage: Fanpage? = null,
    @SerializedName("customer_service")
    var customerService: CustomerService? = null,
    @SerializedName("news_channels")
    var newsChannel: List<Int>? = null,
    @SerializedName("entertainmentChannel")
    var entertainmentChannel: EntertainmentChannel? = null,
    @SerializedName("user_badge_url")
    var userBadgeUrl: String? = null,
    @SerializedName("system_messages")
    var systemMessages: SystemMessages? = null,
    @SerializedName("short_video")
    var shortVideoConfiguration: ShortVideoConfiguration? = null,
    @SerializedName("suggest_gifts")
    var defaultGiftSuggestionConfig: GiftSuggestionConfig? = null
) {
    companion object {
        fun default(): ConfigsData {
            // streamer url
            val streamServer = ArrayList<String>()
            streamServer.add(BuildConfig.ULIVE)

            // comm server
            val commServer = ArrayList<String>()
            commServer.add(BuildConfig.WS)
            commServer.add(BuildConfig.WS2)

            // Short video configuration
            val shortVideoConfiguration = ShortVideoConfiguration(30,
                10240,
                BuildConfig.SHORT_VIDEO_END_POINT)

            // Suggest gift configuration
            val periods = ArrayList<Int>()
            periods.add(180 * 1000)
            periods.add(360 * 1000)
            periods.add(720 * 1000)
            val giftIds = ArrayList<Int>()
            giftIds.add(3)
            giftIds.add(56)
            giftIds.add(92)
            giftIds.add(94)
            giftIds.add(96)
            val defaultGiftSuggestionConfig = GiftSuggestionConfig(periods, giftIds)

            return ConfigsData(
                streamingServer = streamServer,
                commServer = commServer,
                commServerRetryTimeout = 10000,
                commServerPingTime = 60000,
                commServerRetries = 10,
                apiUrl = BuildConfig.API,
                qrLoginUrl = BuildConfig.QRCODELOGIN,
                buyVipUrl = "https://api.360live.vn/api_shop/web/buy_vip",
                shareStreamUrl = "https://360live.vn/live",
                player = 2,
                streamerMode = 2,
                videoWidth = 960,
                videoHeight = 544,
                videoBitrate = 1000000,
                videoFps = 25,
                gameBitrate = 1200000,
                shortVideoConfiguration = shortVideoConfiguration,
                defaultGiftSuggestionConfig = defaultGiftSuggestionConfig,
                h264 = H264.default()
            )
        }

        fun from(json: String?): ConfigsData? {
            return try {
                Gson().fromJson<ConfigsData>(json, ConfigsData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
