package com.zsmx.code_generator.Excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 钊思暮想
 * @date 2024/1/6 20:44
 */
public class ExcelListener extends AnalysisEventListener<Stu> {
    List<Stu> list = new ArrayList<Stu>();

    @Override
    public void invoke(Stu stu, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据"+stu);
        list.add(stu);
    }
    @Override
    //方法是在读取到表头的映射关系时触发的方法，同样，这里的方法没有具体的实现。
    // 如果您需要在读取表头时进行处理，可以在该方法中添加相应的逻辑。
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context){
        System.out.println("表头信息"+headMap);
    }
    @Override
    /*方法是在所有数据读取完成后触发的方法，同样，这里的方法没有具体的实现。
    如果您需要在所有数据读取完成后进行一些收尾工作，可以在该方法中添加相应的逻辑。*/
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public static void main(String[] args) {
        //写法1
        String fileNmae = "/Users/ikun/Documents/复习/ggkt_ershua/ggkt_parent/service/service_vod/src/test/java/com/zsmx/code_generator/Excel/xlsx/1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.read(fileNmae, Stu.class, new ExcelListener()).sheet().doRead();
    }
}
