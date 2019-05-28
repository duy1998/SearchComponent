package com.vng.live.data.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 14/06/2019
 */
data class Profile(
    @SerializedName("avatar120")
    val avatar120: String? = null,
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("avatarVersion")
    val avatarVersion: Int = 0,
    @SerializedName("userName")
    val userName: String? = null,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("point")
    val point: Int = 0,
    @SerializedName("isFollow")
    val isFollow: Int = 0,
    @SerializedName("diamond")
    val diamond: Int = 0,
    @SerializedName("candy")
    val candy: Long = 0,
    @SerializedName("isLive")
    val isLive: Int = 0,
    @SerializedName("avatar60")
    val avatar60: String? = null,
    @SerializedName("phone")
    val phone: Int = 0,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("carrot")
    val star: Int = 0,
    @SerializedName("mute")
    val statusMuteChat: Int = 0,
    @SerializedName("totalFollowing")
    val totalFollowing: Int = 0,
    @SerializedName("totalFan")
    val totalFans: Int = 0,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("about")
    val about: String? = null,
    @SerializedName("topfan")
    val topFans: List<Fan>? = null,
    @SerializedName("sentDiamond")
    val sentDiamond: Long = 0,
    @SerializedName("social")
    val social: Int = 0,
    @SerializedName("job")
    val job: List<Job>? = null,
    @SerializedName("knowledge")
    val knowledge: List<String>? = null,
    @SerializedName("ranking")
    val ranking: Int = 0,
    @SerializedName("dob")
    val birthday: Int = 0,
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("streamType")
    val streamType: Int = 0,
    @SerializedName("attractivePoint")
    val attractivePoint: Int = 0,
    @SerializedName("location_info")
    val locationInfo: LocationInfo? = null,
    @SerializedName("verified")
    val verified: Boolean = false,
    @SerializedName("equipment")
    val equipment: Equipment? = null,
    @SerializedName("isFriend")
    val isFriend: Boolean = false,
    @SerializedName("totalFriend")
    val totalFriends: Int = 0,
    @SerializedName("badges")
    val badgeInfoList: List<BadgeInfo>? = null,
    @SerializedName("vip")
    val vipPriority: VipPriority? = null,
    @SerializedName("banned")
    val isBanned: Boolean = false
)

data class Job(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("company")
    val company: String? = null
)

data class LocationInfo(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("ispId")
    val ispId: Int = 0
)

data class BadgeInfo(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("source")
    val imageUrl: String? = null,
    @SerializedName("expirationAt")
    val expiredDate: Long = 0
)

data class Equipment(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("source")
    val imageUrl: String? = null,
    @SerializedName("expirationAt")
    val expiredDate: Long = 0
)

data class VipPriority(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("expireDate")
    val expireDate: Long = 0,
    @SerializedName("priority")
    val priority: Int = 0,
    @SerializedName("vipGroupId")
    val vipGroupId: Int = 0
)
