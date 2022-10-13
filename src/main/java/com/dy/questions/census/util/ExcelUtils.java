package com.dy.questions.census.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.census.entity.TotalCensus;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {
    public ExcelUtils() {
    }

    public static HSSFFont getFont(HSSFWorkbook workbook, String name, short size, boolean bold) {
        HSSFFont font = workbook.createFont();
        font.setFontName(name);
        font.setFontHeightInPoints(size);
        font.setBold(bold);
        return font;
    }

    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook, HSSFFont font, HorizontalAlignment alignment) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(alignment);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setLocked(true);
        style.setWrapText(true);
        return style;
    }

    public static HSSFCellStyle getStyle(HSSFWorkbook workbook, HSSFFont font, HorizontalAlignment alignment) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(alignment);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setLocked(true);
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public static void doDownload(HSSFWorkbook workbook, String fileName, HttpServletResponse response) {
        response.setContentType("application/x-download;charset=UTF-8");

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (Exception var15) {
            var15.printStackTrace();
        }

        response.addHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + fileName + ".xls");
        OutputStream out = null;

        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }

    }

    public static HSSFWorkbook createExcel(List<TotalCensus> list,String quesCount, String cStore) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        int rowIndex = 0;
        //第一行
        HSSFRow row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(35);

        HSSFCell head = row.createCell(0);
        head.setCellValue("回收答卷数："+quesCount);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        //列表头样式
        HSSFFont headFont = ExcelUtils.getFont(workbook, "黑体", (short) 16, true);
        HSSFCellStyle headStyle = ExcelUtils.getStyle(workbook, headFont, HorizontalAlignment.CENTER);
        head.setCellStyle(headStyle);

        head = row.createCell(5);
        head.setCellValue("满意度总得分："+cStore);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 10));
        //列表头样式
        head.setCellStyle(headStyle);

        HSSFFont font = ExcelUtils.getFont(workbook, "宋体", (short) 12, false);
        HSSFCellStyle style = ExcelUtils.getStyle(workbook, font, HorizontalAlignment.CENTER);

        // 第二行
        row = sheet.createRow(rowIndex++);
        row.setHeightInPoints(30);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue("维度");
        cell = row.createCell(1);
        cell.setCellStyle(headStyle);
        cell.setCellValue("优秀率");
        cell = row.createCell(2);
        cell.setCellStyle(headStyle);
        cell.setCellValue("占比数");
        cell = row.createCell(3);
        cell.setCellStyle(headStyle);
        cell.setCellValue("良好率");
        cell = row.createCell(4);
        cell.setCellStyle(headStyle);
        cell.setCellValue("占比数");
        cell = row.createCell(5);
        cell.setCellStyle(headStyle);
        cell.setCellValue("较好率");
        cell = row.createCell(6);
        cell.setCellStyle(headStyle);
        cell.setCellValue("占比数");
        cell = row.createCell(7);
        cell.setCellStyle(headStyle);
        cell.setCellValue("一般率");
        cell = row.createCell(8);
        cell.setCellStyle(headStyle);
        cell.setCellValue("占比数");
        cell = row.createCell(9);
        cell.setCellStyle(headStyle);
        cell.setCellValue("较差率");
        cell = row.createCell(10);
        cell.setCellStyle(headStyle);
        cell.setCellValue("占比数");

        for(int i=0;i<list.size();i++){
            TotalCensus census = list.get(i);
            if(census == null){
                break;
            }

            row = sheet.createRow(rowIndex++);
            row.setHeightInPoints(30);

            //处理查询
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(census.getQuestion());

            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(census.getExcellentPer());

            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(census.getExcellent());

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(census.getGoodPer());

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(census.getGood());

            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(census.getQuiteGoodPer());

            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(census.getQuiteGood());

            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(census.getCommonlyPer());

            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(census.getCommonly());

            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(census.getPoorPer());

            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(census.getPoor());
        }

        //设置列宽
        sheet.setColumnWidth(0, (12 * 256));
        sheet.setColumnWidth(1, (12 * 256));
        sheet.setColumnWidth(2, (12 * 256));
        sheet.setColumnWidth(3, (12 * 256));
        sheet.setColumnWidth(4, (12 * 256));
        sheet.setColumnWidth(5, (12 * 256));
        sheet.setColumnWidth(6, (12 * 256));
        sheet.setColumnWidth(7, (12 * 256));
        sheet.setColumnWidth(8, (12 * 256));
        sheet.setColumnWidth(9, (12 * 256));
        sheet.setColumnWidth(10, (12 * 256));

        return workbook;
    }
}
