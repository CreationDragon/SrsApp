package com.gtt.app.general;

/**
 * Created by Creat on 2018/4/20.
 */
public class GeneralSetting {
    public static String baseUrl = "http://192.168.1.101:8181/";
    public static String successCaseUrl = baseUrl + "getSuccessCases";
    public static String missInfoUrl = baseUrl + "getMissPersonApp";
    public static String getPersonsPicUrl = baseUrl + "getPersonPic";
    public static String infoSearchUrl = baseUrl + "infoSearch";
    public static String missPeronsInfoDeatailUrl = baseUrl + "getMissPersonsById";
    public static String getSuccessCaseUrl = baseUrl + "getSuccessCases";
    public static String getSiteNewsUrl = baseUrl + "getSiteNotice";
    public static String getAntiTipsUrl = baseUrl + "getAntiFraudiTips";
    public static String getSuccessCasesByIdUrl = baseUrl + "getSuccessCasesById";
    public static String getSiteNoticeByIdUrl = baseUrl + "getSiteNoticeById";
    public static String getAntiFraudTipsByIdUrl = baseUrl + "getAntiFraudTipsById";
    public static String releaseMissInfoUrl = baseUrl + "releaseMissInfo";
    //    public static String uploadImageUrl = baseUrl + "/image/MissPersonPic";
    public static String uploadImageUrl = baseUrl + "/image/upload";
    public static String loginUrl = baseUrl + "appLogin";
    public static String getUserByIdUrl = baseUrl + "admin/getUserInfoById";
    public static String getMissPersonByUserIdUrl = baseUrl + "getMissPersonByUserid";
    public static String setHistoricalRecordsUrl = baseUrl + "setHistoricalRecords";
    public static String getRecordHistoryByUserIdUrl = baseUrl + "getRecordHistoryByUserId";

}
