package com.daimao.filetransfer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yanchenyang958@hellobike.com
 * @date: 2024-06-14 17:56
 */
@Getter
@AllArgsConstructor
public enum FileType {
    FILE("file", "文件"),
    FOLDER("folder", "文件夹");

    private final String code;
    private final String desc;

    public static FileType get(String code) {
        for (FileType value : FileType.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
