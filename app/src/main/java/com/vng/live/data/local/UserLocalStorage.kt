package com.vng.live.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.vng.live.data.GUEST_USER_DISPLAY_NAME
import com.vng.live.data.User
import com.vng.live.data.model.Credential
import com.vng.live.data.model.Fan
import com.vng.live.data.model.Profile
import com.vng.live.data.model.VipPriority
import com.vng.live.util.fromJson
import javax.inject.Inject

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 17/06/2019
 */

class UserLocalStorage @Inject constructor(
    context: Context,
    private val gson: Gson
) {
    private val settings = context.getSharedPreferences(NAME, MODE_PRIVATE)

    @Synchronized
    fun getUserSession(): String? {
        return settings.getString(PREF_USER_SESSION, "")
    }

    @Synchronized
    fun isLoggedIn(): Boolean {
        return getUserSession().isNullOrEmpty()
    }

    @Synchronized
    fun saveUser(user: User?): Boolean {
        if (user == null) {
            return false
        }
        settings.edit()
            .putString(PREF_USER_SESSION, user.credential.accessToken)
            .putInt(PREF_USER_ID, user.profile.userId)
            .apply()

        saveProfile(user.profile)
        return true
    }

    @Synchronized
    fun saveLoginMethod(method: Int) {
        settings.edit().putInt(PREF_USER_LOGIN_METHOD, method).apply()
    }

    @Synchronized
    fun getLoginMethod(): Int {
        return settings.getInt(PREF_USER_LOGIN_METHOD, 0)
    }

    @Synchronized
    fun loadUser(): User {
        return User(
            loadProfile(),
            Credential(settings.getString(PREF_USER_SESSION, "") ?: "")
        )
    }

    @Synchronized
    fun saveProfile(profile: Profile?) {
        if (profile == null) {
            return
        }

        with(settings.edit()) {
            putInt(PREF_USER_ID, profile.userId)
            putString(PREF_USER_NAME, profile.userName)
            putString(PREF_DISPLAY_NAME, profile.displayName)
            putString(PREF_USER_AVATAR_120, profile.avatar120)
            putString(PREF_USER_AVATAR_60, profile.avatar60)
            putString(PREF_USER_EMAIL, profile.email)
            putInt(PREF_USER_MY_STAR, profile.star)
            putInt(PREF_USER_MY_DIAMOND, profile.diamond)
            putLong(PREF_USER_MY_CANDY, profile.candy)
            putInt(PREF_USER_LEVEL, profile.level)
            putInt(PREF_USER_TYPE, profile.type)
            putInt(PREF_USER_TOTAL_FRIEND, profile.totalFriends)
            putInt(PREF_USER_TOTAL_FOLLOWING, profile.totalFollowing)
            putInt(PREF_USER_TOTAL_FAN, profile.totalFans)
            putInt(PREF_USER_BIRTHDAY, profile.birthday)
            putInt(PREF_USER_GENDER, profile.gender)
            putString(PREF_USER_TOP_FANS, gson.toJson(profile.topFans))
            putInt(PREF_USER_SOCIAL, profile.social)
            profile.vipPriority?.apply {
                putString(PREF_USER_VIP_NAME, name)
                putInt(PREF_USER_VIP_PRIORITY, priority)
                putInt(PREF_USER_VIP_GROUP_ID, vipGroupId)
                putLong(PREF_USER_VIP_EXPIRE_DATE, expireDate)
            }
            putBoolean(PREF_IS_BANNED, profile.isBanned)
            apply()
        }
    }

    @Synchronized
    private fun loadProfile(): Profile {
        return with(settings) {
            Profile(
                userId = getInt(PREF_USER_ID, 0),
                userName = getString(PREF_USER_NAME, ""),
                displayName = getString(PREF_DISPLAY_NAME, GUEST_USER_DISPLAY_NAME),
                avatar60 = getString(PREF_USER_AVATAR_60, ""),
                avatar120 = getString(PREF_USER_AVATAR_120, ""),
                email = getString(PREF_USER_EMAIL, ""),
                star = getInt(PREF_USER_MY_STAR, 0),
                diamond = getInt(PREF_USER_MY_DIAMOND, 0),
                candy = getLong(PREF_USER_MY_CANDY, 0),
                level = getInt(PREF_USER_LEVEL, 0),
                type = getInt(PREF_USER_TYPE, 0),
                totalFriends = getInt(PREF_USER_TOTAL_FRIEND, 0),
                totalFollowing = getInt(PREF_USER_TOTAL_FOLLOWING, 0),
                totalFans = getInt(PREF_USER_TOTAL_FAN, 0),
                gender = getInt(PREF_USER_GENDER, 0),
                birthday = getInt(PREF_USER_BIRTHDAY, 0),
                topFans = gson.fromJson<List<Fan>>(getString(PREF_USER_TOP_FANS, "")),
                social = getInt(PREF_USER_SOCIAL, 0),
                vipPriority = VipPriority(
                    name = getString(PREF_USER_VIP_NAME, ""),
                    priority = getInt(PREF_USER_VIP_PRIORITY, -1),
                    vipGroupId = getInt(PREF_USER_VIP_GROUP_ID, -1),
                    expireDate = getLong(PREF_USER_VIP_EXPIRE_DATE, -1)
                ),
                isBanned = getBoolean(PREF_IS_BANNED, false)
            )
        }
    }

    @Synchronized
    fun saveLastUpdateProfileTimestamp(lastFetchTime: Long) {
        settings.edit().putLong(PREF_USER_LAST_UPDATE_PROFILE_TS, lastFetchTime).apply()
    }

    @Synchronized
    fun getLastUpdateProfileTimestamp(): Long {
        return settings.getLong(PREF_USER_LAST_UPDATE_PROFILE_TS, 0)
    }

    @Synchronized
    fun clear() {
        settings.edit().clear().apply()
    }

    @Synchronized
    fun saveLastUpdateFriendRequestsTimestamp(lastGetFriendRequestsTime: Long) {
        settings.edit().putLong(PREF_LAST_UPDATE_FRIEND_REQUESTS_TS, lastGetFriendRequestsTime).apply()
    }

    @Synchronized
    fun getLastUpdateFriendRequestsTimestamp(): Long {
        return settings.getLong(PREF_LAST_UPDATE_FRIEND_REQUESTS_TS, 0)
    }

    @Synchronized
    fun saveLastSyncOfflineMessagesTimestamp(timestamp: Long) {
        settings.edit().putLong(PREF_LAST_SYNC_OFFLINE_MESSAGE_TS, timestamp).apply()
    }

    @Synchronized
    fun getLastSyncOfflineMessagesTimestamp(): Long {
        return settings.getLong(PREF_LAST_SYNC_OFFLINE_MESSAGE_TS, 0)
    }

    @Synchronized
    fun saveTotalFriend(totalFriends: Int) {
        settings.edit().putInt(PREF_USER_TOTAL_FRIEND, totalFriends).apply()
    }

    @Synchronized
    fun saveTotalFollowing(totalFollowing: Int) {
        settings.edit().putInt(PREF_USER_TOTAL_FOLLOWING, totalFollowing).apply()
    }

    @Synchronized
    fun isStreamShareFacebook(): Boolean {
        return settings.getBoolean(PREF_USER_STREAM_SHARE_FACEBOOK, false)
    }

    @Synchronized
    fun saveStreamShareFacebook(isShare: Boolean) {
        settings.edit().putBoolean(PREF_USER_STREAM_SHARE_FACEBOOK, isShare).apply()
    }

    @Synchronized
    fun saveFacebookToken(token: String) {
        settings.edit().putString(PREF_USER_FACEBOOK_TOKEN, token).apply()
    }

    @Synchronized
    fun saveZaloToken(token: String) {
        settings.edit().putString(PREF_USER_ZALO_TOKEN, token).apply()
    }

    @Synchronized
    fun getFacebookToken(): String? {
        return settings.getString(PREF_USER_FACEBOOK_TOKEN, "")
    }

    @Synchronized
    fun saveGoogleToken(token: String) {
        settings.edit().putString(PREF_USER_GOOGLE_TOKEN, token).apply()
    }

    @Synchronized
    fun getGoogleToken(): String? {
        return settings.getString(PREF_USER_GOOGLE_TOKEN, "")
    }

    @Synchronized
    fun getZaloToken(): String? {
        return settings.getString(PREF_USER_ZALO_TOKEN, "")
    }

    @Synchronized
    fun getLastUpdateCheckInGiftDetails(): Long {
        return settings.getLong(PREF_LAST_UPDATE_CHECK_IN_GIFT_DETAILS, 0)
    }

    @Synchronized
    fun saveLastUpdateCheckInGiftDetails(lastUpdateTimestamp: Long) {
        settings.edit().putLong(PREF_LAST_UPDATE_CHECK_IN_GIFT_DETAILS, lastUpdateTimestamp).apply()
    }

    @Synchronized
    fun shouldRemindCheckIn(): Int {
        return settings.getInt(PREF_SHOULD_REMIND_CHECK_IN, 1)
    }

    @Synchronized
    fun saveRemindCheckInFlag(shouldRemindFlag: Int) {
        settings.edit().putInt(PREF_SHOULD_REMIND_CHECK_IN, shouldRemindFlag).apply()
    }

    @Synchronized
    fun getLastShownCheckInDialogTimestamp(): Long {
        return settings.getLong(PREF_LAST_SHOWN_CHECK_IN_DIALOG_TS, 0)
    }

    @Synchronized
    fun saveLastShownCheckInDialogTimestamp(timestamp: Long) {
        settings.edit().putLong(PREF_LAST_SHOWN_CHECK_IN_DIALOG_TS, timestamp).apply()
    }

    @Synchronized
    fun hasCheckedIn(): Int {
        return settings.getInt(PREF_HAS_CHECKED_IN, 0)
    }

    @Synchronized
    fun setHasCheckedIn(status: Int) {
        settings.edit().putInt(PREF_HAS_CHECKED_IN, status).apply()
    }

    @Synchronized
    fun saveDiamond(diamond: Int) {
        settings.edit().putInt(PREF_USER_MY_DIAMOND, Math.max(0, diamond)).apply()
    }

    @Synchronized
    fun getDiamond(): Int {
        return settings.getInt(PREF_USER_MY_DIAMOND, 0)
    }

    @Synchronized
    fun saveCandy(candy: Long) {
        settings.edit().putLong(PREF_USER_MY_CANDY, candy).apply()
    }

    @Synchronized
    fun getCandy(): Long {
        return settings.getLong(PREF_USER_MY_CANDY, 0)
    }

    @Synchronized
    fun saveStreamCameraBeautyLevel(level: Float) {
        settings.edit().putFloat(PREF_STREAM_CAMERA_BEAUTY_LEVEL, level).apply()
    }

    @Synchronized
    fun getStreamCameraBeautyLevel(): Float {
        return settings.getFloat(PREF_STREAM_CAMERA_BEAUTY_LEVEL, 0.9f)
    }

    @Synchronized
    fun saveStreamCameraBrightLevel(level: Float) {
        settings.edit().putFloat(PREF_STREAM_CAMERA_BRIGHT_LEVEL, level).apply()
    }

    @Synchronized
    fun getStreamCameraBrightLevel(): Float {
        return settings.getFloat(PREF_STREAM_CAMERA_BRIGHT_LEVEL, 0.5f)
    }

    @Synchronized
    fun saveStreamCameraToneLevel(level: Float) {
        settings.edit().putFloat(PREF_STREAM_CAMERA_TONE_LEVEL, level).apply()
    }

    @Synchronized
    fun getStreamCameraToneLevel(): Float {
        return settings.getFloat(PREF_STREAM_CAMERA_TONE_LEVEL, 0.4f)
    }

    @Synchronized
    fun saveStreamCameraFilter(hasBeauty: Boolean) {
        settings.edit().putBoolean(PREF_STREAM_CAMERA_HAS_FILTER, hasBeauty).apply()
    }

    @Synchronized
    fun hasStreamCameraFilter(): Boolean {
        return settings.getBoolean(PREF_STREAM_CAMERA_HAS_FILTER, true)
    }

    @Synchronized
    fun saveVipName(vipName: String) {
        settings.edit().putString(PREF_USER_VIP_NAME, vipName).apply()
    }

    @Synchronized
    fun getVipName(): String? {
        return settings.getString(PREF_USER_VIP_NAME, "")
    }

    @Synchronized
    fun saveVipPriority(priority: Int) {
        settings.edit().putInt(PREF_USER_VIP_PRIORITY, priority).apply()
    }

    @Synchronized
    fun getVipPriority(): Int {
        return settings.getInt(PREF_USER_VIP_PRIORITY, 0)
    }

    @Synchronized
    fun saveVipGroupId(groupId: Int) {
        settings.edit().putInt(PREF_USER_VIP_GROUP_ID, groupId).apply()
    }

    @Synchronized
    fun getVipGroupId(): Int {
        return settings.getInt(PREF_USER_VIP_GROUP_ID, 0)
    }

    @Synchronized
    fun saveVipExpireDate(expireDay: Long) {
        settings.edit().putLong(PREF_USER_VIP_EXPIRE_DATE, expireDay).apply()
    }

    @Synchronized
    fun getVipExpireDate(): Long {
        return settings.getLong(PREF_USER_VIP_EXPIRE_DATE, -1)
    }

    @Synchronized
    fun setHasDeniedLocationPermission(hasDeniedLocationPermission: Boolean) {
        settings.edit().putBoolean(PREF_HAS_DENIED_LOCATION_PERMISSION, hasDeniedLocationPermission).apply()
    }

    @Synchronized
    fun hasDeniedLocationPermission(): Boolean {
        return settings.getBoolean(PREF_HAS_DENIED_LOCATION_PERMISSION, false)
    }

    @Synchronized
    fun setLastRemindUpdateAppTime(timestamp: Long) {
        settings.edit().putLong(PREF_LAST_REMIND_UPDATE_APP_TIME, timestamp).apply()
    }

    @Synchronized
    fun getLastRemindUpdateAppTime(): Long {
        return settings.getLong(PREF_LAST_REMIND_UPDATE_APP_TIME, 0)
    }

    fun setCompletedMissions(completedMissions: Int) {
        settings.edit().putInt(PREF_COMPLETED_MISSIONS, completedMissions).apply()
    }

    fun getCompletedMissions(): Int {
        return settings.getInt(PREF_COMPLETED_MISSIONS, 0)
    }

    fun setHasReceiveNewUserReward(flag: Boolean) {
        settings.edit().putBoolean(PREF_HAS_RECEIVE_NEW_USER_REWARD, flag).apply()
    }

    fun hasReceiveNewUserReward(): Boolean {
        return settings.getBoolean(PREF_HAS_RECEIVE_NEW_USER_REWARD, false)
    }

    fun setHasSentNewUserGift(flag: Boolean) {
        settings.edit().putBoolean(PREF_HAS_SENT_NEW_USER_GIFT, flag).apply()
    }

    fun hasSentNewUserGift(): Boolean {
        return settings.getBoolean(PREF_HAS_SENT_NEW_USER_GIFT, false)
    }

    fun setHasGainFirstMissionReward(flag: Boolean) {
        settings.edit().putBoolean(PREF_HAS_GAIN_FIRST_MISSION_REWARD, flag).apply()
    }

    fun hasGainFirstMissionReward(): Boolean {
        return settings.getBoolean(PREF_HAS_GAIN_FIRST_MISSION_REWARD, false)
    }

    fun setNewUserRewardQuantity(quantity: Int) {
        settings.edit().putInt(PREF_NEW_USER_REWARD_QUANTITY, quantity).apply()
    }

    fun getNewUserRewardQuantity(): Int {
        return settings.getInt(PREF_NEW_USER_REWARD_QUANTITY, 0)
    }

    fun setIsNewRegisterUser(flag: Int) {
        settings.edit().putInt(PREF_IS_NEW_REGISTER_USER, flag).apply()
    }

    fun getIsNewRegisterUser(): Int {
        return settings.getInt(PREF_IS_NEW_REGISTER_USER, 0)
    }

    fun getLastShowTaggingFlowTimeStamp(): Long {
        return settings.getLong(PREF_SHOW_TAGGING_FLOW, -1)
    }

    fun setLastShowTaggingFlowTimeStamp(date: Long) {
        settings.edit().putLong(PREF_SHOW_TAGGING_FLOW, date).apply()
    }

    fun getHintShowed(): Int {
        return settings.getInt(PREF_TAGGING_HINT_SHOWED, -1)
    }

    fun markAsHintShowed() {
        settings.edit().putInt(PREF_TAGGING_HINT_SHOWED, 1).apply()
    }

    fun getShowedUserInfoCollector(): Int {
        return settings.getInt(PREF_TAGGING_USER_INFO_COLLECTOR_SHOWED, 0)
    }

    fun markShowedUserInfoCollector() {
        settings.edit().putInt(PREF_TAGGING_USER_INFO_COLLECTOR_SHOWED, 1).apply()
    }

    fun isFirstInstall(): Int {
        return settings.getInt(PREF_IS_FIRST_PLAY_STREAM, 1)
    }

    fun markNotFirstInstall() {
        settings.edit().putInt(PREF_IS_FIRST_PLAY_STREAM, 0).apply()
    }

    companion object {
        private const val NAME = "user_prefs"
        private const val PREF_USER_SESSION = "key_user_session"
        private const val PREF_USER_ID = "key_user_id"
        private const val PREF_USER_LOGIN_METHOD = "key_user_login_method"
        private const val PREF_USER_NAME = "key_user_name_key"
        private const val PREF_DISPLAY_NAME = "key_display_name_key"
        private const val PREF_USER_AVATAR_120 = "key_user_avatar_120_key"
        private const val PREF_USER_AVATAR_60 = "key_user_avatar_60_key"
        private const val PREF_USER_EMAIL = "key_user_email"
        private const val PREF_USER_LEVEL = "key_user_level"
        private const val PREF_USER_BIRTHDAY = "key_user_birth_date"
        private const val PREF_USER_GENDER = "key_user_gender"
        private const val PREF_USER_PHONE = "key_user_phone"
        private const val PREF_USER_MY_DIAMOND = "key_user_diamond"
        private const val PREF_USER_MY_CANDY = "key_user_candy"
        private const val PREF_USER_MY_STAR = "key_user_carrot"
        private const val PREF_USER_TYPE = "key_user_type"
        private const val PREF_USER_TOTAL_FAN = "key_user_total_fan"
        private const val PREF_USER_TOTAL_FOLLOWING = "key_user_total_following"
        private const val PREF_USER_TOTAL_FRIEND = "key_user_total_friend"
        private const val PREF_USER_LAST_UPDATE_PROFILE_TS = "key_last_update_profile_ts"
        private const val PREF_LAST_UPDATE_FRIEND_REQUESTS_TS = "key_last_update_friend_requests_ts"
        private const val PREF_USER_TOP_FANS = "key_user_top_fans"
        private const val PREF_USER_SOCIAL = "key_user_social"
        private const val PREF_IS_BANNED = "key_is_banned"
        private const val PREF_USER_FACEBOOK_TOKEN = "key_user_facebook_token"
        private const val PREF_USER_ZALO_TOKEN = "key_user_zalo_token"
        private const val PREF_USER_GOOGLE_TOKEN = "key_user_google_token"
        private const val PREF_USER_STREAM_SHARE_FACEBOOK = "key_user_stream_share_facebook"
        private const val PREF_LAST_SYNC_OFFLINE_MESSAGE_TS = "key_last_sync_offline_message_ts"
        private const val PREF_LAST_UPDATE_CHECK_IN_GIFT_DETAILS = "key_last_update_check_in_gift_details"
        private const val PREF_SHOULD_REMIND_CHECK_IN = "key_remind_check_in_flag"
        private const val PREF_LAST_SHOWN_CHECK_IN_DIALOG_TS = "key_last_shown_check_in_dialog_ts"
        private const val PREF_HAS_CHECKED_IN = "key_has_checked_in"
        private const val PREF_STREAM_CAMERA_BEAUTY_LEVEL = "key_stream_camera_beauty_level"
        private const val PREF_STREAM_CAMERA_BRIGHT_LEVEL = "key_stream_camera_bright_level"
        private const val PREF_STREAM_CAMERA_TONE_LEVEL = "key_stream_camera_tone_level"
        private const val PREF_STREAM_CAMERA_HAS_FILTER = "key_stream_camera_has_filter"
        private const val PREF_USER_VIP_NAME = "key_user_vip_name"
        private const val PREF_USER_VIP_PRIORITY = "key_user_vip_priority"
        private const val PREF_USER_VIP_GROUP_ID = "key_user_vip_group_id"
        private const val PREF_USER_VIP_EXPIRE_DATE = "key_user_vip_expiredate"
        private const val PREF_HAS_DENIED_LOCATION_PERMISSION = "key_has_deny_location_permission"
        private const val PREF_LAST_REMIND_UPDATE_APP_TIME = "key_last_remind_update_app_time"
        private const val PREF_COMPLETED_MISSIONS = "key_completed_missions"
        private const val PREF_IS_NEW_REGISTER_USER = "key_is_new_register_user"
        private const val PREF_HAS_RECEIVE_NEW_USER_REWARD = "key_has_receive_new_user_reward"
        private const val PREF_HAS_SENT_NEW_USER_GIFT = "key_has_sent_new_user_gift"
        private const val PREF_HAS_GAIN_FIRST_MISSION_REWARD = "key_has_receive_first_mission_reward"
        private const val PREF_NEW_USER_REWARD_QUANTITY = "key_new_user_reward_quantity"
        private const val PREF_SHOW_TAGGING_FLOW = "key_show_tagging_flow"
        private const val PREF_TAGGING_HINT_SHOWED = "key_tagging_hint_showed"
        private const val PREF_TAGGING_USER_INFO_COLLECTOR_SHOWED = "key_tagging_user_info_collection_permission"
        private const val PREF_IS_FIRST_PLAY_STREAM = "key_is_first_play_stream"
    }
}
