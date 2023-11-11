package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RequestMapping("/common")
@RestController
public class CommonController {

    @Value("${reggie.upload-path}")
    private String BASE_PATH;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(BASE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(BASE_PATH + newFileName));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.success(newFileName);
    }


    /**
     * 这里我们通过流的方式下载文件，所以不需要放回
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void downloadJ(String name, HttpServletResponse response) {
        try {
            String fileName = BASE_PATH + name;
            // 输入流，读入本地文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));

            // 输出流，将内容传送给前端
            ServletOutputStream outputStream = response.getOutputStream();

            // TODO: 这里要如何检测文件类型？
            response.setContentType("image/jpeg");

            // 如果想要将文件表示为附件，则可以这样：
            // response.setHeader(CONTENT_DISPOSITION, "attachment; filename=" + name);

            // 每次传送 1KB
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            // 完成后记得关闭资源
            outputStream.close();
            fileInputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
