package com.njbd.pt.utils.file;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/6-16:24.on NJBD
 */
public class FileUtil {


    private String allowSuffix = "jpg,png,gif,jpeg,mp4";//允许文件格式
    private long allowSize = 2500 * 10240;//允许文件大小250kb
    private float allowWidth = 30000;
    private float allowHeight = 30000;
    private String fileName;
    private String[] fileNames;


    public String getAllowSuffix() {
        return allowSuffix;
    }

    public void setAllowSuffix(String allowSuffix) {
        this.allowSuffix = allowSuffix;
    }

    public long getAllowSize() {
        return allowSize;
    }

    public void setAllowSize(long allowSize) {
        this.allowSize = allowSize;
    }

    public float getAllowWidth() {
        return allowWidth;
    }

    public void setAllowWidth(float allowWidth) {
        this.allowWidth = allowWidth;
    }

    public float getAllowHeight() {
        return allowHeight;
    }

    public void setAllowHeight(float allowHeight) {
        this.allowHeight = allowHeight;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }


    /**
     * 文件名称前缀格式化
     *
     * @return
     */
    private String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }


    public Map upload(MultipartFile file, Integer allowWidth, Integer allowHeight, String destDir, HttpServletRequest request) throws Exception {
        Map returnMap = new HashMap();
        String path = request.getSession().getServletContext().getRealPath("WEB-INF/views/");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            int length = getAllowSuffix().indexOf(suffix.toLowerCase());
            if (length == -1) {
                returnMap.put("code", 1);
                returnMap.put("msg", "请上传允许格式的文件");
                return returnMap;
            }
            File destFile = new File(path + destDir);//文件夹创建
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            String fileNameNew = getFileNameNew() + "." + suffix;
            File f = new File(destFile.getAbsoluteFile() + "/" + fileNameNew);
            file.transferTo(f);                // 转存文件
            f.createNewFile();
            if (allowWidth != null && allowHeight != null) {
                BufferedImage bufferedImage = ImageIO.read(f);
                if (bufferedImage == null) {
                    f.delete();
                    returnMap.put("code", 1);
                    returnMap.put("msg", "文件已损坏，请上传有效的文件!");
                    return returnMap;
                }
                int imageWidth = bufferedImage.getWidth();
                int imageHeight = bufferedImage.getHeight();
                if (imageWidth > allowWidth.intValue() || imageHeight > allowHeight.intValue()) {
                    f.delete();
                    returnMap.put("code", 1);
                    returnMap.put("msg", String.format("请上传指定大小的图片，最大尺寸不超过%dx%d", allowWidth.intValue(), allowHeight.intValue()));
                    return returnMap;
                }
            }
            fileName = basePath + destDir + fileNameNew;
            String retFileName = destDir + "/" + fileNameNew;

            Map tempMap = new HashMap();
            tempMap.put("url", retFileName);
            returnMap.put("code", 0);
            returnMap.put("msg", "文件上传成功!");
            returnMap.put("data", tempMap);
            return returnMap;
        } catch (Exception e) {
            returnMap.put("code", -1);
            returnMap.put("msg", "文件上传失败,请重新上传");
            return returnMap;
        }
    }


    /**
     * 多文件上传
     *
     * @param files
     * @param destDir
     * @param request
     * @throws Exception
     */
    public void uploads(MultipartFile[] files, String destDir, HttpServletRequest request) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("WEB-INF/views/");//相对路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        try {
            fileNames = new String[files.length];
            int index = 0;
            for (MultipartFile file : files) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                int length = getAllowSuffix().indexOf(suffix);
                if (length == -1) {
                    throw new Exception("请上传允许格式的文件");
                }
                File destFile = new File(path + destDir);
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew() + "." + suffix;//
                File f = new File(destFile.getAbsoluteFile() + "\\" + fileNameNew);
                file.transferTo(f);
                f.createNewFile();
                fileNames[index++] = basePath + destDir + fileNameNew;
            }
        } catch (Exception e) {
            throw e;
        }
    }


}
