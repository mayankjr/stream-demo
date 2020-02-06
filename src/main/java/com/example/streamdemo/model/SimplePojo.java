package com.example.streamdemo.model;

import com.example.streamdemo.converter.ObjectIdConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

/**
 * Created by mayank on 06/02/20
 */
public class SimplePojo {

    @JsonSerialize(converter = ObjectIdConverter.class)
    private ObjectId objectId;

    public SimplePojo(ObjectId objectId) {
        this.objectId = objectId;
    }

    public SimplePojo() {}

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "objectId=" + objectId +
                '}';
    }
}
