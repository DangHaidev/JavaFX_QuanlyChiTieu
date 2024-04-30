package javaproject.expense_management;

import java.sql.Date;

public class DailyData {
    private String daily_ID;
    private String Type;
    private String LoaiThuChi;
    private int  SoTien;
    private String TaiKhoan;
    private String Note;
    private Date date;

    public DailyData(String dailyID, String Type, String LoaiTC, int SoTien, String TaiKhoan,String Note ,Date date)
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

    public int getSoTien() {
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
}
