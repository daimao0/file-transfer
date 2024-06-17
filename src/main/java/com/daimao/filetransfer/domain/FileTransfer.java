package com.daimao.filetransfer.domain;

import cn.hutool.core.collection.CollUtil;
import com.daimao.filetransfer.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yanchenyang958@hellobike.com
 * @date: 2024-06-14 17:11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class FileTransfer {
    /**
     * 文件名称/文件名
     */
    private String name;
    /**
     * 文件类型
     */
    private FileType type;
    /**
     * 文件地址
     */
    private String path;
    /**
     * 文件地址
     */
    private String upperPath;
    /**
     * 子文件
     */
    private List<FileTransfer> subFiles;

    /**
     * 通过路径获得文件传输类
     *
     * @param path 路径
     * @return 文件传输类
     */
    public static FileTransfer getByLocation(String path) {
        File file = new File(path);
        FileTransfer fileTransfer = toFileTransfer(file);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                List<FileTransfer> subFiles = new ArrayList<>();
                for (File one : files) {
                    subFiles.add(toFileTransfer(one));
                }
                fileTransfer.setSubFiles(subFiles);
            }
            //获得上一层
            String[] split = fileTransfer.getPath().split("/");
            StringBuilder upperPath = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                upperPath.append(split[i]).append("/");
            }
            fileTransfer.setUpperPath((split.length==0)?"/":upperPath.toString());
        }
        return fileTransfer;
    }

    /**
     * 文件传输类型转化
     *
     * @param file 文件
     * @return 文件
     */
    private static FileTransfer toFileTransfer(File file) {
        return new FileTransfer()
                .setName(file.getName())
                .setType(file.isFile() ? FileType.FILE : FileType.FOLDER)
                .setPath(file.getAbsolutePath());
    }
}
