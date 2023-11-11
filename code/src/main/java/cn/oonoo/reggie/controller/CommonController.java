package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RequestMapping("/common")
@RestController
public class CommonController {

    @Value("${reggie.upload-path}")
    private String BASE_PATH;

    /**
     * 上传图片
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
}
