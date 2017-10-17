package com.phapps.elitedangerous.eddn.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JournalDto {

    /**
     * (Required)
     */
    @SerializedName("$schemaRef")
    @Expose
    private String mSchemaRef;
    /**
     * (Required)
     */
    @SerializedName("header")
    @Expose
    private Header mHeader;
    /**
     * Contains all properties from the listed events in the client's journal minus Localised
     * strings and the properties marked below as 'disallowed'
     * (Required)
     */
    @SerializedName("message")
    @Expose
    private Message mMessage;

    /**
     * (Required)
     */
    public String getSchemaRef() {
        return mSchemaRef;
    }

    /**
     * (Required)
     */
    public void setSchemaRef(String schemaRef) {
        this.mSchemaRef = schemaRef;
    }

    /**
     * (Required)
     */
    public Header getHeader() {
        return mHeader;
    }

    /**
     * (Required)
     */
    public void setHeader(Header header) {
        this.mHeader = header;
    }

    /**
     * Contains all properties from the listed events in the client's journal minus Localised
     * strings and the properties marked below as 'disallowed'
     * (Required)
     */
    public Message getMessage() {
        return mMessage;
    }

    /**
     * Contains all properties from the listed events in the client's journal minus Localised
     * strings and the properties marked below as 'disallowed'
     * (Required)
     */
    public void setMessage(Message message) {
        this.mMessage = message;
    }
}
