package com.example.sv.Model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class MultipartFileToByteArrayConverter implements Converter<MultipartFile, byte[]> {

    @Override
    public byte[] convert(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            // Xử lý ngoại lệ (IOException) nếu cần thiết
            e.printStackTrace();
        }
        return null;
    }
}