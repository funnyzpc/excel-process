##  Excel 导出工具集
```
    excel此为Excel导出工具集，以poi.xssf技术为基础实现
```

### 功能
+ 导出xlsx格式Excel文件
    - [ExcelWriteUtil](./src/main/java/com/excel/process/util/ExcelWriteUtil.java)

+ 读取xlsx格式Excel文件数据
    - [ExcelReadUtil](./src/main/java/com/excel/process/util/ExcelReadUtil.java)

### 接口api
+ 导出工具类 ExcelWriteUtil
    - 对象集合导出 ：_toXlsxByObj_ 
    - Map集合导出 ：_toXlsxByMap_

+ 导入工具类ExcelReadUtil
    - 读取数据转Map_readXlsx2Map_

### 样例 example
+ [ExcelWriteTest](./src/test/com/excel/process/test/ExcelWriteTest.java)
+ [ExcelReadTest](./src/test/com/excel/process/test/ExcelReadTest.java)
