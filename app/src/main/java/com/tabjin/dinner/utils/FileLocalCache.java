package com.tabjin.dinner.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;


public class FileLocalCache {


    public static boolean checkDir(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return true;
    }


    public static String md5(String url) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes("UTF-8"));
            byte messageDigest[] = md5.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String t = Integer.toHexString(0xFF & messageDigest[i]);
                if (t.length() == 1) {
                    hexString.append("0" + t);
                } else {
                    hexString.append(t);
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object getSerializableData(Context context, String fileName) {
        String dir = FileUtil.getCacheDir(context);
        Object obj = null;
        try {
            File d = new File(dir);
            if (!d.exists()) {
                d.mkdirs();
            }
            File f = new File(dir + md5(fileName));
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() == 0) {
                return null;
            }
            FileInputStream byteOut = new FileInputStream(f);
            ObjectInputStream out = new ObjectInputStream(byteOut);
            obj = out.readObject();
            out.close();
            try {
                if (byteOut != null) {
                    byteOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static void setSerializableData(Context context, Object obj, String fileName) {
        String dir = FileUtil.getCacheDir(context);
        try {
            FileOutputStream bytetOut = new FileOutputStream(new File(dir + md5(fileName)));
            ObjectOutputStream outer = new ObjectOutputStream(bytetOut);
            outer.writeObject(obj);
            outer.flush();
            outer.close();
            bytetOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void delSerializableData(Context context, String fileName) {
        String dir = FileUtil.getCacheDir(context);
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdirs();
        }
        File f = new File(dir + md5(fileName));
        if (f.exists()) {
            f.delete();
        }
    }
}
