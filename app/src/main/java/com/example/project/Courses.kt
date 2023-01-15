package com.example.project

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.icu.text.Normalizer.NO
import android.net.Uri
import android.text.TextUtils
import java.lang.IllegalArgumentException
import java.util.HashMap

class CoursesProvider() : ContentProvider() {
    companion object {
        val PROVIDER_NAME = "com.example.MyApplication.CoursesProvider"
        val URL = "content://" + PROVIDER_NAME + "/courses"
        val CONTENT_URI = Uri.parse(URL)
        val COURSEID ="courseid"
        val COURSENAME = "coursename"
        val HOURS = "hours"
        val GRADE = "grade"
        private val COURSES_PROJECTION_MAP: HashMap<String, String>? = null
        val COURSES = 1
        val COURSE_ID = 2
        val uriMatcher: UriMatcher? = null
        val DATABASE_NAME = "University"
        val COURSES_TABLE_NAME = "Courses"
        val DATABASE_VERSION = 1
        val CREATE_DB_TABLE = " CREATE TABLE " + COURSES_TABLE_NAME +
                " (courseid INTEGER PRIMARY KEY AUTOINCREMENT, " + " coursename TEXT NOT NULL, " +
                " hours INTEGER NOT NULL, " + "grade INTEGER NOT NULL);"
        private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH);
        init
        {

            sUriMatcher.addURI(PROVIDER_NAME, "courses", COURSES);
            sUriMatcher.addURI(PROVIDER_NAME, "courses/#", COURSE_ID);
        }

    }
    private var db: SQLiteDatabase? = null

    private class DatabaseHelper internal constructor(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME)
            onCreate(db)
        }
    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)

        db = dbHelper.writableDatabase
        return if (db == null) false else true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val rowID = db!!.insert(COURSES_TABLE_NAME, "", values)
        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }


    override fun query(
        uri: Uri, projection: Array<String>?,
        selection: String?, selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = COURSES_TABLE_NAME
        when (uriMatcher!!.match(uri)){
            COURSE_ID -> qb.appendWhere(COURSEID+"="+uri.pathSegments[1])
            else -> {null}
        }


        if (sortOrder == null || sortOrder === "") {

            sortOrder = COURSENAME
        }
        val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)

        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher!!.match(uri)) {
            COURSES -> count = db!!.delete(
                COURSES_TABLE_NAME, selection,
                selectionArgs
            )
            COURSE_ID -> {
                val id = uri.pathSegments[1]
                count = db!!.delete(
                    COURSES_TABLE_NAME,
                    COURSEID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                    selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var count = 0
        when (uriMatcher!!.match(uri)) {
            COURSES -> count = db!!.update(
                COURSES_TABLE_NAME, values, selection,
                selectionArgs
            )
            COURSE_ID -> count = db!!.update(
                COURSES_TABLE_NAME,
                values,
                COURSEID + " = " + uri.pathSegments[1] + (if (!TextUtils.isEmpty(selection)) " AND ($selection)" else ""),
                selectionArgs
            )
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher!!.match(uri)) {
            COURSES -> return "vnd.android.cursor.dir/vnd.example.courses"
            COURSE_ID -> return "vnd.android.cursor.item/vnd.example.courses"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }
}