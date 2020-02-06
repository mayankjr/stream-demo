package com.example.streamdemo.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mayank on 06/02/2020
 * Using this converter we can avoid ObjectId serialization problem.
 */
public class ObjectIdConverter extends StdConverter<List<ObjectId>, List<String>> {
    @Override
    public List<String> convert(List<ObjectId> value) {
        return value.stream().map(ObjectId::toHexString).collect(Collectors.toList());
    }
}
