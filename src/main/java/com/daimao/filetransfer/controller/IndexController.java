package com.daimao.filetransfer.controller;

import com.daimao.filetransfer.domain.FileTransfer;
import com.daimao.filetransfer.service.TransferService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author: yanchenyang958@hellobike.com
 * @date: 2024-06-14 16:24
 */
@Controller
public class IndexController {

    private final TransferService transferService;

    public IndexController(TransferService transferService) {
        this.transferService = transferService;
    }


    @GetMapping({"/", "/index"})
    public String listUser(Model model) {
        FileTransfer fileTransfer = transferService.listFileTransfer();
        //添加对象
        model.addAttribute("fileTransfer", fileTransfer);
        return "index";
    }

    @GetMapping("/next")
    public String next(String path, Model model) {
        FileTransfer fileTransfer = transferService.next(path);
        model.addAttribute("fileTransfer", fileTransfer);
        return "index";
    }

    @GetMapping("/download")
    public void download(String path, HttpServletResponse response) {
        File file = new File(path);
        if (file.exists()) {
            String fileName = file.getName();
            //设置下载不打开
            response.setContentType("application/force-download");
            response.setContentType("application/octet-stream");
            //设置文件名
            // 对于现代浏览器，使用RFC 5987兼容的编码方式
            String rfc5987FileName = "filename*=UTF-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.addHeader("Content-Disposition", "attachment;" + rfc5987FileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            byte[] buffer = new byte[4096];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                OutputStream out = response.getOutputStream();
                int read = bufferedInputStream.read(buffer);
                while (read != -1) {
                    out.write(buffer, 0, read);
                    read = bufferedInputStream.read(buffer);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
