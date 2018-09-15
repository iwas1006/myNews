package com.han.mynews.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

public class FileCache {

    Context context;

    public FileCache(Context co) {
        context = co;
    }

    public File getCacheDir(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if(!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }
        if(!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    public void write(String fileName, byte[] fileBytes) {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, fileName);
        if(!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //FileWriter fileWriter = new FileWriter(cacheFile);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cacheFile);
                try {
                    fos.write(fileBytes);
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public String writeFile(String fileName, File f) {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, fileName);

        Log.d("fileName____",fileName);

        if(cacheFile.exists()) {
            Log.d("fileName33____", "cacheFile.exists()");
        } else {
            Log.d("fileName33____", "!cacheFile.exists()");
        }

        if(f.exists()) {
            Log.d("fileName33____", "f.exists()");
        } else {
            Log.d("fileName33____", "!f.exists()");
        }

        Log.d("fffffffffffa____", f.length()+"");
        Log.d("fffffffffffa____", cacheFile.length()+"");

        f.renameTo(cacheFile);

        Log.d("fffffffffff222____", f.length()+"");
        Log.d("fffffffffff22____", cacheFile.length()+"");

        Log.d("fileName2____",cacheFile.getName());
        return cacheFile.getName();

        /*if(!cacheFile.exists()) {
            try {
             //   cacheFile.createNewFile();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;*/
    }

    public byte[] read(String fileName) {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, fileName);
        if(!cacheFile.exists()) return null;

       byte[] returnBytes = new byte[(int) cacheFile.length()];

        try {
            FileInputStream inputStream = new FileInputStream(cacheFile);
            inputStream.read(returnBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnBytes;
    }

    public File readFile(String fileName) {
        File cacheDir = getCacheDir(context);

        if(context == null) {
            Log.d("aaa____________", "context==null");
        } else {
            Log.d("aaa____________", "context!=null");
        }
        if(cacheDir == null) {
            Log.d("aaa____________", "cacheDir==null");
        } else {
            Log.d("aaa____________", "cacheDir!=null");
        }
        if(fileName == null) {
            Log.d("aaa____________", "fileName==null");
        } else {
            Log.d("aaa____________", "fileName!=null");
        }

        File cacheFile = new File(cacheDir, fileName);
        if(!cacheFile.exists()) return null;
        return cacheFile;
    }

    public Bitmap convertFileToBitmap(byte[] b) {
       return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public void convertBitmapToFile(Bitmap b) {
     //   b.
    }

    public String makeFileName(String filePath) {
        try {
            return URLEncoder.encode(filePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
