package com.tjohnn.teleprompter.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

public class FileUtils {

    public static String getMimeType(Context context, Uri uri){
        ContentResolver resolver = context.getContentResolver();
        return resolver.getType(uri);
    }

    @Nullable
    public static String getFileDisplayName(Context context, Uri uri){
        ContentResolver resolver = context.getContentResolver();

        String displayName;
        try (Cursor cursor = resolver
                .query(uri, null, null, null, null, null)) {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                return displayName;
            }
            return null;
        }
    }

    public static String getExtension(Context context, Uri uri){
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(getMimeType(context, uri));
    }

}
