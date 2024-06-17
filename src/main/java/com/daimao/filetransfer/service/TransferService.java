package com.daimao.filetransfer.service;

import com.daimao.filetransfer.FileTransferApplication;
import com.daimao.filetransfer.domain.FileTransfer;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author: yanchenyang958@hellobike.com
 * @date: 2024-06-14 17:20
 */
@Service
public class TransferService {
    /**
     * 获得当前jar包所在的文件夹文件
     * 展示文件传输
     *
     * @return 文件
     */
    public FileTransfer listFileTransfer() {
        String desktopPath = System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop";
        return FileTransfer.getByLocation(desktopPath);
    }

    public FileTransfer next(String path) {
        return FileTransfer.getByLocation(path);
    }
}
