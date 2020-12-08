package com.mb.myawesomequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mb.myawesomequiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        insertCategory(c1);
        Category c2 = new Category("Geography");
        insertCategory(c2);
        Category c3 = new Category("Math");
        insertCategory(c3);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        //PROGRAMMING - EASY
        Question q1 = new Question("ซอฟต์แวร์ คือ",
                "โปรแกรมชุดของคำสั่งที่ควบคุมการทำงานของคอมพิวเตอร์", "อุปกรณ์เทคโนโลยีระดับสูง", "โปรแกรมแก้ปัญหาทุกอย่างของมนุษย์", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q1);
        Question q2 = new Question("ข้อใดไม่ใช่ระบบปฏิบัติการ",
                "ระบบปฏิบัติการดอส", "ระบบปฏิบัติการไมโครซอฟท์เวิร์ด", "ระบบปฏิบัติการแอนดรอยด์", 2,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q2);
        Question q3 = new Question("ชนิดของซอฟต์แวร์ (software) มีทั้งหมดกี่ชนิด",
                "มี 1 ชนิด 1.ซอฟต์แวร์ระบบ", "มี 2 ชนิด 1.ซอฟต์แวร์ระบบ 2.ซอฟต์แวร์ประยุกต์", "มี 3 ชนิด 1.ซอฟต์แวร์ระบบ 2.ซอฟต์แวร์ ประยุกต์ 3.ซอฟต์แวร์บุคคล", 2,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q3);

        //PROGRAMMING - MEDIUM
        Question q4 = new Question("ซอฟต์แวร์ประมวลคำ คือข้อใด",
                "word processing software", "spreadsheet software", "presentation software", 1,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q4);
        Question q5 = new Question("ซอฟต์แวร์ตารางทำงาน",
                "word processing software", "spreadsheet software", "database management software", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q5);
        Question q6 = new Question("ซอฟต์แวร์จัดการฐานข้อมูล",
                "spreadsheet software", "word processing software", "database management software", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q6);

        //PROGRAMMING - HARD
        Question q7 = new Question("ข้อใด ไม่ใช่ระบบปฏิบัติการ",
                "Android", "Windows Mobile", "ถูกทุกข้อ คือ ปฏิบัติการ", 3,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q7);
        Question q8 = new Question("ผู้ก่อตั้งและผู้สร้างFacebook.com คือใคร",
                "มาร์ค คาลเบิร์ก", "มาร์ค ซัคเกอร์เบิร์ก", "ฟาร์ค ซัคเกอร์เบิร์ก", 2,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q8);
        Question q9 = new Question("ข้อใดคือ ซอฟต์แวร์",
                "ครูสอนคอมพิวเตอร์", "กล้องถ่ายภาพดิจิตอล", "โปรแกรมMicrosoft PowerPoint", 3,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q9);

        //GEOGRAPHY - EASY
        Question q10 = new Question("เทือกเขาหลวงพระบาง กั้นพรมแดนไทยกับประเทศใด",
                "พม่า", "จีน", "ลาว", 3,
                Question.DIFFICULTY_EASY, Category.GEOGRAPHY);
        insertQuestion(q10);
        Question q11 = new Question("อุทยานแห่งชาติแก่งตะนะ อยู่จังหวัดใด",
                "เชียงใหม่", "อุบลราชธานี", "กระบี่", 2,
                Question.DIFFICULTY_EASY, Category.GEOGRAPHY);
        insertQuestion(q11);
        Question q12 = new Question("พระปรางค์สามยอดอยู่จังหวัดใด",
                "ลพบุรี", "อยุธยา", "เชียงใหม่", 1,
                Question.DIFFICULTY_EASY, Category.GEOGRAPHY);
        insertQuestion(q12);

        //GEOGRAPHY - MEDIUM
        Question q13 = new Question("แม่น้ำปิง มีต้นกำเนิดจากจังหวัดใด",
                "เชียงใหม่", "ลำปาง", "น่าน", 1,
                Question.DIFFICULTY_MEDIUM, Category.GEOGRAPHY);
        insertQuestion(q13);
        Question q14 = new Question("แม่น้ำตาปี มีอีกชื่อหนึ่งว่าอะไร",
                "แม่น้ำพุมดวง", "แม่น้ำหลวง", "แม่น้ำเขาราช", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOGRAPHY);
        insertQuestion(q14);
        Question q15 = new Question("เขตรักษาพันธุ์สัตว์ป่าลุ่มน้ำปาย อยู่จังหวัดใด",
                "แม่ฮ่องสอน", "ชุมพร", "ตาก", 1,
                Question.DIFFICULTY_MEDIUM, Category.GEOGRAPHY);
        insertQuestion(q15);

        //GEOGRAPHY - HARD
        Question q16 = new Question("หนองน้ำที่ใหญ่ที่สุดของไทย คืออะไร",
                "หนองบวกหาด เชียงใหม่", "หนองบรเพชร เพชรบุรี", "หนองหาน สกลนคร", 3,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q16);
        Question q17 = new Question("นครขุขันธ์ คือจังหวัดใด",
                "นครศรีธรรมราช", "ศรีสะเกษ", "นครศรีธรรมราช", 2,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q17);
        Question q18 = new Question("เขื่อนสิรินธร มีชื่ออีกชื่อหนึ่งว่าอะไร",
                "เขื่อนผาซ่อม", "เขื่อนเจ้าเณร", "เขื่อนลำโดมน้อย", 3,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q18);

        //MATH - EASY
        Question q19 = new Question("ผลรวมของ 1 ถึง 3 เท่ากับเท่าใด",
                "3", "6", "9", 2,
                Question.DIFFICULTY_EASY, Category.MATH);
        insertQuestion(q19);
        Question q20 = new Question("ถ้า 5x = 5 แล้ว x เท่ากับเท่าใด",
                "1", "5", "10", 1,
                Question.DIFFICULTY_EASY, Category.MATH);
        insertQuestion(q20);
        Question q21 = new Question("จำนวนใดเป็นเลขคู่",
                "287", "357", "678", 3,
                Question.DIFFICULTY_EASY, Category.MATH);
        insertQuestion(q21);

        //MATH - MEDIUM
        Question q22 = new Question("ถ้า A + B = 7 และ A - B = 5 แล้วค่าของ A คือเท่าใด",
                "8", "7", "6", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATH);
        insertQuestion(q22);
        Question q23 = new Question("ถ้า 5x = 5 แล้ว 2x + 2 เท่ากับเท่าใด",
                "2", "4", "14", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATH);
        insertQuestion(q23);
        Question q24 = new Question("เมื่อ 10 ปีก่อน พ่ออายุมากกว่าแม่ 5 ปี ปัจจุบันพ่ออายุมากกว่าแม่เท่าใด",
                "5", "8", "10", 1,
                Question.DIFFICULTY_MEDIUM, Category.MATH);
        insertQuestion(q24);

        //MATH - HARD
        Question q25 = new Question("เลขจำนวนเต็มบวกคี่ 3 จำนวนเรียงกัน รวมกันได้ 15 เลขที่น้อยสุดคือเลขอะไร",
                "1", "3", "5", 2,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q25);
        Question q26 = new Question("นางขวัญ จ่ายเงิน 80 บาท ซื้อเสื้อซึ่งมีส่วนลด 20% ถามว่าราคาก่อนลด คือ เท่าใด",
                "100", "150", "200", 1,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q26);
        Question q27 = new Question(" เสารั้วบ้านปักห่างกัน 2 เมตร ระหว่างต้นที่ 1 กับ 13 ห่างกันกี่เมตร",
                "12", "24", "28", 2,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q27);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
