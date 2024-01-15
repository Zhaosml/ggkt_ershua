package com.zsmx.code_generator.Excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 钊思暮想
 * @date 2024/1/6 20:33
 */
@Data
public class Stu {
    @ExcelProperty("学生编号")
    private int sno;
    @ExcelProperty("学生姓名")
    private String name;

    //循环设置要添加的数据，最终封装到list集合中
    private static List<Stu> data(){
        List<Stu> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Stu stu = new Stu();
            stu.setSno(i);
            stu.setName("张飞"+i);
            list.add(stu);
        }
        return list;
    }

    public static void main(String[] args) {
        //写法1
        String fileNmae = "/Users/ikun/Documents/复习/ggkt_ershua/ggkt_parent/service/service_vod/src/test/java/com/zsmx/code_generator/Excel/xlsx/1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileNmae, Stu.class).sheet("写操作").doWrite(data());
    }
}
