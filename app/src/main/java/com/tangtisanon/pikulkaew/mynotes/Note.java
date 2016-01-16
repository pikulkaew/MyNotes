package com.tangtisanon.pikulkaew.mynotes;

/**
 * Created by k on 16/1/2559.
 */

/*ร้างclass ที่มี attribute ที่ตรงกับคอลัมในฐานข้อมูล class ข้างนอกเรียกอ่านได้เลยเป็น public จะได้ไม่ต้องสร้าง setter getter
แต่ไม่ให้แก้ใช้ finalแต่อนุญาตให้ class ข้างนอกกำหนดค่าโดยใช้ constructor เพียงครั้งเดียวเท่านั้นหรือ final a = 10; ประกาศตรงไฟนอลเลยได้
กดที่หลอดไฟเลือก constructor
ปกติตัวแปรใน class ควรเป็น private เพราะถ้า pyblic ข้างนอกเซตค่าติดลบมาโปรแกรมจะพัง
*/
public class Note {
    public final int noteId;
    public final String text;
    public final boolean isImportant;

    public Note(int noteId, String text, boolean isImportant) {
        this.noteId = noteId;
        this.text = text;
        this.isImportant = isImportant;
    }
    // crtl+O เพื่อแปลงเป็น tostring ของแต่ละ object

    @Override
    public String toString() {
        return String.valueOf(noteId)
                + " - "
                + text
                + " - "
                + String.valueOf(isImportant);
    }
}
