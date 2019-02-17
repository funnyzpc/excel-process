package com.excel.process.util;

import com.excel.process.other.UUIDUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author: funnyzpc
 * @Description 读取xlsx
 **/
public class ExcelReadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReadUtil.class);

    /**
     * 当前操作系统目录分隔符
     */
    private static final String FILE_SYSTEM_SEPARATOR_KEY = "file.separator";

    private static final String SYSTEM_TMP_DIR;
    static {
        if(System.getProperty(FILE_SYSTEM_SEPARATOR_KEY).equals("/")){
            SYSTEM_TMP_DIR = "/tmp/import_dir";
        }else{
            SYSTEM_TMP_DIR = "D:/tmp/import_dir";
        }
    }

    /**
     * 读取Excel并转换为Map数据（上传文件方式）
     * @param multipartFile 上传文件
     * @param colCount 读取最大列
     * @return
     */
    public static List<Map<String,String>> readXlsx2Map(MultipartFile multipartFile, int colCount){
        File tmpFile = new File(SYSTEM_TMP_DIR);
        if(!tmpFile.exists()){
            tmpFile.mkdirs();
        }
        tmpFile = new File(String.format("%s%s%s",SYSTEM_TMP_DIR,File.separator, UUIDUtil.shortUUID(),multipartFile.getOriginalFilename()));
        try {
            multipartFile.transferTo(tmpFile);
            return readXlsx2Map(tmpFile,colCount);
        }catch (IOException iOException){
            LOGGER.error("\r\n>>>文件导入异常:",iOException);
            tmpFile.delete();
        }
        return null;
    }

     /***
      * 读取Excel并转换为Map数据（本地文件形式）
      * @param file 本地文件
      * @param colCount 读取最大列
      */
    public static List<Map<String,String>> readXlsx2Map(File file, int colCount) {
        List<Map<String,String>> varList = new ArrayList<>();
        try(FileInputStream fi = new FileInputStream(file);
        	XSSFWorkbook wb = new XSSFWorkbook(fi);
        ){
            //sheet 从0开始
            XSSFSheet sheet= wb.getSheetAt(0);
            //取得最后一行的行号
            int rowNum = sheet.getLastRowNum() + 1;
            //第二行循环开始 startrow:1
            for (int i = 1; i < rowNum; i++) {
                //行
                XSSFRow row = sheet.getRow(i);
                if(null == row ){
                    break;
                }
                Map<String,String> varpd = new Hashtable<>();
                //每行的最后一个单元格位置
                //int cellNum = row.getLastCellNum();
                //每行的第一列循环开始 startcol
                for (int j = 0; j < colCount; j++) {
                    XSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    String cellType = null;
                    if (null != cell && !"".equals(cell.getCellTypeEnum().name())) {
                        // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                        cellType = cell.getCellTypeEnum().name();
                        if("STRING".equals(cellType)){
                            cellValue=cell.getStringCellValue();
                        }else if("NUMERIC".equals(cellType)){
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }else if("BOOLEAN".equals(cellType)){
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                        }else if("BLANK".equals(cellType)){
                            cellValue = "";
                            //当前行第一列为空或null的直接跳过
                            if(0 == j){
                                break;
                            }
                        }else{
                            LOGGER.error("导入>未知的数据类型!");
                        }
                    }else{
                        //当前行第一列为空或null的直接跳过
                        if(0 == j){
                            break;
                        }
                        continue;
                    }
                    varpd.put("var"+j, cellValue);
                }
                //若单账号为空
                if(!varpd.isEmpty()) {
                    varList.add(varpd);
                }
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
            LOGGER.error("错误：",iOException);
        }
        //删除传入文件
        file.delete();
        return varList;
    }

}