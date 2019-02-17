package com.excel.process.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author funnyzpc
 */
public class CreateMultipleSheetDemo {

    private final static Object object = new Object();

    @Test
    public void processor(){
        int cell = Runtime.getRuntime().availableProcessors();
        System.out.println(cell);
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        //处理器核心数
        int processor = Runtime.getRuntime().availableProcessors();
        //XSSFWorkbook 一次只能写入六万多条数据，所以这里最好使用SXSSFWorkbook
        SXSSFWorkbook workBook = new SXSSFWorkbook();
        //创建格式
        CellStyle style = workBook.createCellStyle();
        //居中格式
        style.setAlignment(HorizontalAlignment.CENTER);
        //手工创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(processor, processor, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(),
                new ThreadFactoryBuilder().setNameFormat("poi-task-%d").build());
        //计数器 等待线程池中的线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(processor);
        for (int i = 0; i < processor; i++) {
            int sheetId = i;
            //放入线程池中
            executorService.execute(() -> createSheet(workBook, style,sheetId, countDownLatch));
        }
        try {
            //等待所有线程执行完毕
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FileOutputStream fou = null;
        try {
            fou = new FileOutputStream("/tmp/multiSheetNew.xlsx");
            workBook.write(fou);
            workBook.dispose();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fou != null) {
                try {
                    fou.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null !=workBook){
                try {
                    workBook.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("\r\n=====>");
        System.out.println(end-start);
    }

    private static void createSheet(SXSSFWorkbook workBook, CellStyle style, int sheetId, CountDownLatch countDownLatch) {
        try {
            SXSSFSheet hSSFSheet;
            //这个地方一定要加锁，要不然会出现问题
            synchronized (object) {
                //创建sheet页
                hSSFSheet = workBook.createSheet(String.format("第%d个sheet页", sheetId));
            }
            //创建一行
            SXSSFRow hssfRow = hSSFSheet.createRow(0);
            SXSSFCell hssfCell = hssfRow.createCell(0);
            hssfCell.setCellStyle(style);
            hssfCell.setCellValue("第" + sheetId + "个sheet页，第一行，第一个单元格");

            hssfCell = hssfRow.createCell(1);
            hssfCell.setCellStyle(style);
            hssfCell.setCellValue("第" + sheetId + "个sheet页，第一行，第二个单元格");

            hssfCell = hssfRow.createCell(2);
            hssfCell.setCellStyle(style);
            hssfCell.setCellValue("第" + sheetId + "个sheet页，第一行，第三个单元格");
            SXSSFRow hssfRows;
            SXSSFCell hSSFCells;
            for (int i = 1; i < 100; i++) {
                hssfRows = hSSFSheet.createRow(i);
                hSSFCells = hssfRows.createCell(0);
                hSSFCells.setCellStyle(style);
                hSSFCells.setCellValue("第" + sheetId + "个sheet页，第" + (i + 1) + "行，第一个单元格");

                hSSFCells = hssfRows.createCell(1);
                hSSFCells.setCellStyle(style);
                hSSFCells.setCellValue("第" + sheetId + "个sheet页，第一个单元格");

                hSSFCells = hssfRows.createCell(2);
                hSSFCells.setCellStyle(style);
                hSSFCells.setCellValue("第" + sheetId + "个sheet页，第一个单元格");
            }
        } finally {
            //计数器减一
            countDownLatch.countDown();
        }
    }
}
