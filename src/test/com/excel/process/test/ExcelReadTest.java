package com.excel.process.test;

import com.excel.process.util.ExcelReadUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ExcelReadTest {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelReadTest.class);

    /**
     * read data by xlsx
     */
    @Test
    public void readXlsx(){
        File file = new File("/private/tmp/export_dir/20190217B9D64AF8.xlsx");
        List<Map<String,String>> results = ExcelReadUtil.readXlsx2Map(file,8);
        LOG.info("读取结果 ===> \r\n {}",results);
    }
}
