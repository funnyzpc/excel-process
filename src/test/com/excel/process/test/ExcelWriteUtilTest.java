package com.excel.process.test;

import com.excel.process.other.DateUtil;
import com.excel.process.util.ExcelWriteUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: funnyzpc
 * @Description 导出工具类测试
 **/
public class ExcelWriteUtilTest {
    @Test
    public void showSystem(){
        System.out.println( System.getProperty("os.name") );
        System.out.println( System.getProperty("os.arch") );
        System.out.println( System.getProperty("path.separator") );
        System.out.println( System.getProperty("file.separator") );
    }

    @Test
    public void generateExcel(){
        List<BookDTO> dataList = new ArrayList<>();

        BookDTO blog = new BookDTO();
        blog.setId(9999999999L);
        blog.setName("《树上的男爵》");
        blog.setLabel("推荐");
        blog.setDatePublication(new Date());
        blog.setStatusPublication(true);
        blog.setSerialPublication(new BigDecimal(77894588756L));
        blog.setCreateTime(new Timestamp(System.currentTimeMillis()));

        BookDTO blog2 = new BookDTO();
        blog2.setId(9999999999L);
        blog2.setName("《动物农庄》");
        blog2.setLabel("精选");
        blog2.setDatePublication(Date.from(LocalDate.of(2018,9,9).atStartOfDay(DateUtil.CHINA_ZONE_ID).toInstant()));
        blog2.setStatusPublication(false);
        blog2.setSerialPublication(new BigDecimal(66999410016L));
        blog2.setCreateTime(new Timestamp(new Date().getTime()));
        blog2.setPrice(23.456D);


        BookDTO blog3 = new BookDTO();
        blog3.setId(9999999999L);
        blog3.setName("《小王子》");
        blog3.setLabel("儿童");
        blog3.setDatePublication(Date.from(LocalDate.of(2018,9,9).atStartOfDay(DateUtil.CHINA_ZONE_ID).toInstant()));
        blog3.setStatusPublication(false);
        blog3.setSerialPublication(new BigDecimal(66999410016L));
        blog3.setCreateTime(new Timestamp(new Date().getTime()));
        blog3.setPrice(23558.46D);

        BookDTO blog4 = new BookDTO();
        blog4.setId(9999999999L);
        blog4.setName("《礼记》");
        blog4.setLabel("古典");
        blog4.setDatePublication(Date.from(LocalDate.of(2018,12,9).atStartOfDay(DateUtil.CHINA_ZONE_ID).toInstant()));
        blog4.setStatusPublication(false);
        blog4.setSerialPublication(new BigDecimal(9522112L));
        blog4.setCreateTime(new Timestamp(new Date().getTime()));
        blog4.setPrice(23558.46D);

        dataList.add(blog);
        dataList.add(blog2);
        dataList.add(blog3);
        dataList.add(blog4);

        String[] headerNames = {"书籍编号 \r\n Book Number","书籍名称 \r\n Book Name","售价\r\nPrice","标签\r\nLabel",
                "出版日期 \r\n Publish Date","发行状态\r\nStatus","发行编号 \r\n Publish SerialNo","记录创建时间\r\nCreate Time"};
        String[] cellNames = {"id","name","price","label","datePublication","statusPublication","serialPublication","createTime"};
        //Integer[] cellLengthValue={1000,2000,2000,null,4000,null};
        ExcelWriteUtil.toXlsxByObj(dataList,headerNames,cellNames,null);
    }


    @Test
    public void testReflect(){
        LocalDate ld =LocalDate.ofEpochDay(System.currentTimeMillis());

        System.out.println(ld.getYear());
        System.out.println(ld.getMonth());
        System.out.println(ld.getDayOfMonth());

        //ReflectionUtils.findField()
    }

    @Test
    public void testTypeName(){
        BookDTO dto = new BookDTO();
        Long ll = 88888999L;
        BigDecimal bd = new BigDecimal(9999888);
        System.out.println(dto.getClass().getName());
        System.out.println(dto.getClass().getTypeName());

        System.out.println(ll.getClass().getName());
        System.out.println(ll.getClass().getTypeName());

        System.out.println(bd.getClass().getName());
        System.out.println(bd.getClass().getTypeName());

    }
}
