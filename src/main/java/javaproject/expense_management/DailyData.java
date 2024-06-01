package javaproject.expense_management;

import java.sql.Date;

public class DailyData {
    private String daily_ID;
    private String Type;
    private String LoaiThuChi;
    private String  SoTien;
    private String TaiKhoan;
    private String Note;
    private Date date;

    public DailyData(String dailyID, String Type, String LoaiTC, String SoTien, String TaiKhoan,String Note ,Date date)
    {
        this.daily_ID = dailyID;
        this.Type = Type;
        this.LoaiThuChi = LoaiTC;
        this.SoTien = SoTien;
        this.TaiKhoan = TaiKhoan;
        this.Note = Note;
        this.date = date;
    }
    public String getDaily_ID() {
        return daily_ID;
    }
    public String getType() {
        return Type;
    }

    public String getLoaiThuChi() {
        return LoaiThuChi;
    }

    public String getSoTien() {
        return SoTien;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public String getNote() {
        return Note;
    }

    public Date getDate() {
        return date;
    }

    public static String temp_Type;
    public static String temp_LoaiThuChi;
    public static String temp_SoTien;
    public static String temp_TaiKhoan;
    public static String temp_Note;
    public static Date temp_date;

    public static void setTemp_Type(String temp_Type) {
        DailyData.temp_Type = temp_Type;
    }

    public static void setTemp_LoaiThuChi(String temp_LoaiThuChi) {
        DailyData.temp_LoaiThuChi = temp_LoaiThuChi;
    }

    public static void setTemp_SoTien(String temp_SoTien) {
        DailyData.temp_SoTien = temp_SoTien;
    }

    public static void setTemp_TaiKhoan(String temp_TaiKhoan) {
        DailyData.temp_TaiKhoan = temp_TaiKhoan;
    }

    public static void setTemp_Note(String temp_Note) {
        DailyData.temp_Note = temp_Note;
    }

    public static String getTemp_Type() {
        return temp_Type;
    }

    public static String getTemp_LoaiThuChi() {
        return temp_LoaiThuChi;
    }

    public static String getTemp_SoTien() {
        return temp_SoTien;
    }

    public static String getTemp_TaiKhoan() {
        return temp_TaiKhoan;
    }

    public static String getTemp_Note() {
        return temp_Note;
    }

    public static Date getTemp_date() {
        return temp_date;
    }
}
