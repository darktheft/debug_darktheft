package com.videozone.sdkrepo;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class JitsiMeetConferenceOptions implements Parcelable {
    private URL serverURL;
    private String room;
    private String subject;
    private String token;
    private Bundle colorScheme;
    private Bundle featureFlags;
    private Boolean audioMuted;
    private Boolean audioOnly;
    private Boolean videoMuted;
    //private JitsiMeetUserInfo userInfo;
    public static final Creator<JitsiMeetConferenceOptions> CREATOR = new Creator<JitsiMeetConferenceOptions>() {
        public JitsiMeetConferenceOptions createFromParcel(Parcel in) {
            return new JitsiMeetConferenceOptions(in);
        }

        public JitsiMeetConferenceOptions[] newArray(int size) {
            return new JitsiMeetConferenceOptions[size];
        }
    };

    private JitsiMeetConferenceOptions() {
    }

    private JitsiMeetConferenceOptions(Parcel in) {
        this.room = in.readString();
        this.subject = in.readString();
        this.token = in.readString();
        this.colorScheme = in.readBundle();
        this.featureFlags = in.readBundle();
        //this.userInfo = new JitsiMeetUserInfo(in.readBundle());
        byte tmpAudioMuted = in.readByte();
        this.audioMuted = tmpAudioMuted == 0 ? null : tmpAudioMuted == 1;
        byte tmpAudioOnly = in.readByte();
        this.audioOnly = tmpAudioOnly == 0 ? null : tmpAudioOnly == 1;
        byte tmpVideoMuted = in.readByte();
        this.videoMuted = tmpVideoMuted == 0 ? null : tmpVideoMuted == 1;
    }

    Bundle asProps() {
        Bundle props = new Bundle();
        if (!this.featureFlags.containsKey("pip.enabled")) {
            this.featureFlags.putBoolean("pip.enabled", true);
        }

        props.putBundle("flags", this.featureFlags);
        if (this.colorScheme != null) {
            props.putBundle("colorScheme", this.colorScheme);
        }

        Bundle config = new Bundle();
        if (this.audioMuted != null) {
            config.putBoolean("startWithAudioMuted", this.audioMuted);
        }

        if (this.audioOnly != null) {
            config.putBoolean("startAudioOnly", this.audioOnly);
        }

        if (this.videoMuted != null) {
            config.putBoolean("startWithVideoMuted", this.videoMuted);
        }

        if (this.subject != null) {
            config.putString("subject", this.subject);
        }

        Bundle urlProps = new Bundle();
        if (this.room != null && this.room.contains("://")) {
            urlProps.putString("url", this.room);
        } else {
            if (this.serverURL != null) {
                urlProps.putString("serverURL", this.serverURL.toString());
            }

            if (this.room != null) {
                urlProps.putString("room", this.room);
            }
        }

        if (this.token != null) {
            urlProps.putString("jwt", this.token);
        }

//        if (this.token == null && this.userInfo != null) {
//            props.putBundle("userInfo", this.userInfo.asBundle());
//        }

        urlProps.putBundle("config", config);
        props.putBundle("url", urlProps);
        return props;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.room);
        dest.writeString(this.subject);
        dest.writeString(this.token);
        dest.writeBundle(this.colorScheme);
        dest.writeBundle(this.featureFlags);
        //dest.writeBundle(this.userInfo != null ? this.userInfo.asBundle() : new Bundle());
        dest.writeByte((byte)(this.audioMuted == null ? 0 : (this.audioMuted ? 1 : 2)));
        dest.writeByte((byte)(this.audioOnly == null ? 0 : (this.audioOnly ? 1 : 2)));
        dest.writeByte((byte)(this.videoMuted == null ? 0 : (this.videoMuted ? 1 : 2)));
    }

    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private URL serverURL;
        private String room;
        private String subject;
        private String token;
        private Bundle colorScheme;
        private Bundle featureFlags = new Bundle();
        private Boolean audioMuted;
        private Boolean audioOnly;
        private Boolean videoMuted;
        //private JitsiMeetUserInfo userInfo;

        public Builder() {
        }

        public JitsiMeetConferenceOptions.Builder setServerURL(URL url) {
            this.serverURL = url;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setRoom(String room) {
            this.room = room;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setColorScheme(Bundle colorScheme) {
            this.colorScheme = colorScheme;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setAudioMuted(boolean muted) {
            this.audioMuted = muted;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setAudioOnly(boolean audioOnly) {
            this.audioOnly = audioOnly;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setVideoMuted(boolean videoMuted) {
            this.videoMuted = videoMuted;
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setWelcomePageEnabled(boolean enabled) {
            this.featureFlags.putBoolean("welcomepage.enabled", enabled);
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setFeatureFlag(String flag, boolean value) {
            this.featureFlags.putBoolean(flag, value);
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setFeatureFlag(String flag, String value) {
            this.featureFlags.putString(flag, value);
            return this;
        }

        public JitsiMeetConferenceOptions.Builder setFeatureFlag(String flag, int value) {
            this.featureFlags.putInt(flag, value);
            return this;
        }

//        public JitsiMeetConferenceOptions.Builder setUserInfo(JitsiMeetUserInfo userInfo) {
//            this.userInfo = userInfo;
//            return this;
//        }

        public JitsiMeetConferenceOptions build() {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions();
            options.serverURL = this.serverURL;
            options.room = this.room;
            options.subject = this.subject;
            options.token = this.token;
            options.colorScheme = this.colorScheme;
            options.featureFlags = this.featureFlags;
            options.audioMuted = this.audioMuted;
            options.audioOnly = this.audioOnly;
            options.videoMuted = this.videoMuted;
            //options.userInfo = this.userInfo;
            return options;
        }
    }
}

