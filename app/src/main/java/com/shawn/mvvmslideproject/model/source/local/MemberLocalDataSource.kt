package com.shawn.mvvmslideproject.model.source.local

import com.google.gson.Gson
import com.shawn.mvvmslideproject.model.source.local.MemberInfo.memberInfo
import com.shawn.mvvmslideproject.util.sharePreference.Preference
import com.shawn.mvvmslideproject.util.sharePreference.PreferenceKeys.CLIENT_INFO
import com.shawn.mvvmslideproject.util.sharePreference.PreferenceNames.MEMBER_INFO
import javax.inject.Inject


data class MemberLocalData(
    val memberId: Int = 0,
    val memberName: String = "",
    val memberGender: String = "",
    val memberBirth: String = ""
)

object MemberInfo{
    var memberInfo: MemberLocalData? = null
}

class MemberLocalDataSource@Inject constructor() {
    private val gson: Gson = Gson()
    private var profileSharePreference by Preference(CLIENT_INFO, MEMBER_INFO, "")

    fun getMemberInfo(): MemberLocalData {
        memberInfo = gson.fromJson(profileSharePreference, MemberLocalData::class.java)
        return memberInfo?: MemberLocalData()
    }

    fun setMemberInfo(info: MemberLocalData) {
        profileSharePreference = gson.toJson(info)
        memberInfo = info
    }

    fun clearMemberInfo() {
            profileSharePreference = ""
            setMemberInfo(MemberLocalData())
            saveId(0)
            memberInfo = null
    }

    //onlyId
    fun saveId(id: Int) {
        var memberInfo = getMemberInfo() as MemberLocalData

        memberInfo.apply {
            memberInfo = MemberLocalData(memberId = id)
            setMemberInfo(memberInfo)
        }
    }

    fun getMemberId() = getMemberInfo().memberId

    fun hasMemberId(): Boolean {
        return getMemberInfo()?.memberId != 0
//        if (memberInfo == null) {
//            memberInfo = getMemberInfo()
//        }
//        return memberInfo?.let {
//            it.memberId != 0 && it.memberId > 0
//        } == true
    }

}