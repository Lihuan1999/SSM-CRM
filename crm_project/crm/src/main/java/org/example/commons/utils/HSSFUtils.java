package org.example.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * 封装了关于Excel操作的工具类，获取单元格中的数据
 */
public class HSSFUtils {

    public static String getCellValueFoeStr(HSSFCell cell){
        String ret = "";
        if (cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            ret = cell.getStringCellValue();
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            ret = cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            ret = cell.getCellFormula();
        }else{
            ret = "";
        }
        return ret;
    }
}
