package com.lch.bills.utils;

import com.lch.bills.pojo.Bills;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelUtils {

    public static void main(String[] args) {
        List<Bills> list = new ArrayList<>();
        Bills bills = new Bills();
        bills.setRemake("remake").setMoney(12.3D).setCreateDate(new Date()).setType(1).setName("辅助");
        list.add(bills);

        createExcel(list);
    }


    public static String createExcel(List<Bills> list){
        FileOutputStream fos=null;
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("账单列表");
        String[] title={"创建时间","类型","金额","图标类型名","备注"};
        Row row=sheet.createRow((short)0);
        int i=0;
        for(String s:title){
            Cell cell=row.createCell(i);
            cell.setCellValue(s);
            i++;
        }
        int j=1;
        for(Bills t:list){
            //创建第二行
            Row rowData=sheet.createRow((short)j);
            //第一列数据
            Cell cell0=rowData.createCell((short)0);
            cell0.setCellValue(t.getCreateDate());
            //设置单元格的宽度
            sheet.setColumnWidth((short)0, (short)4000);
            //第二列数据
            Cell cell1=rowData.createCell((short)1);
            cell1.setCellValue(t.getType()==1?"支出":"收入");
            //设置单元格的宽度
            sheet.setColumnWidth((short)1, (short)4000);
            //第三列数据
            Cell cell2=rowData.createCell((short)2);
            cell2.setCellValue(t.getMoney());
            //设置单元格的宽度
            sheet.setColumnWidth((short)2, (short)4000);
            //第四列数据
            Cell cell3=rowData.createCell((short)3);
            cell3.setCellValue(t.getName());
            //设置单元格的宽度
            sheet.setColumnWidth((short)3, (short)4000);
            //第五列数据
            Cell cell4=rowData.createCell((short)4);
            cell4.setCellValue(t.getRemake());
            //设置单元格的宽度
            sheet.setColumnWidth((short)4, (short)4000);
            j++;
        }
        try {
            //导出数据库文件保存路径 //"D:/export.xlsx"  /bills/exportFile/
//            String basePath = "D:/bills/";
            String basePath = "/bills/exportFile/";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            String fileName = UUID.randomUUID().toString().replaceAll("-", "");
            String dateStr = sdf.format(date);

//          String filePath = basePath + dateStr + "/"+ fileName + ".xlsx";//linux下
            String filePath = "D:/"+fileName +".xlsx"; //window下
            System.out.println(filePath);

            File file = new File(filePath);
            if (!file.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            fos=new  FileOutputStream(filePath);
            //将工作簿写入文件
            workbook.write(fos);

            fos.close();
            return filePath;
//            System.out.println("导出文件成功");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            System.out.println("导出文件失败");
            return null;
        }
    }
}
