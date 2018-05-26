package com.gtt.app.Util;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Creat on 2018/5/11.
 */

public class Note {

    public Note() {
    }

    public static final class Notes implements BaseColumns {
        private Notes() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + NotesContentProvider.AUTHORITY + "/notes");

        public static final String NOTES_TYPE = "vnd.android.cursor.dir/NOTES";
        public static final String NOTES_ID_TYPE = "vnd.android.cursor.item/NOTES";

        public static final String NOTE_ID = "_id";

        public static final String TITLE = "title";

        public static final String TEXT = "text";
    }

}
