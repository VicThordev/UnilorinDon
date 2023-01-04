package com.folahan.unilorinapp.Model;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_SURNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PREFERENCE_NAME = "unilorinDonPreference";
    public static final String KEY_IS_SIGNED_IN = "sign_in";
    public static final String KEY_IS_SIGNED_UP = "sign_up";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_POSTER_ID = "posterId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String KEY_COLLECTION_COMMENT = "com.folahan.unilorin_don.extra_comment";
    public static final String KEY_POSTER_NAME = "poster_name";
    public static final String KEY_PAID = "unpaid";
    public static final String KEY_TRUE_PAID = "true_paid";
    public static final String KEY_LAST_COMMENT = "lastComment";
    public static final String KEY_COLLECTION_QUESTION = "questions";
    public static final String KEY_LAST_QUESTION = "lastQuestion";
    public static final String KEY_QUESTION_POST = "question";
    public static final String KEY_COLLECTION_POST = "question";
    public static final String KEY_QUESTION_BOX = "questionBox";
    public static final String KEY_FACULTY = "faculty";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_LIKES_BOX = "likesBox";
    public static final String KEY_TYPE = "image";
    public static final String REMOTE_MSG_AUTHORIZATION ="Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content_Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION = "registration_ids";


    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAAAAAAAAA"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application,json"
            );
        }
        return remoteMsgHeaders;
    }

}
