package studio.unispace.simplywords.models;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by haof on 8/17/2016.
 */
public class Library {

    private static final String TAG = "Library.java";

    public List<String> dictionaries;

    public Library () {
        dictionaries = new LinkedList<>();
    }

    public boolean addDictionary (Context ctx, String name) {
        for (String dict : dictionaries) {
            if (dict.equals(name)) {
                return false;
            }
        }
        dictionaries.add(name);
        saveDictionary(ctx, name);
        return true;
    }

    public boolean removeDictionary (Context ctx, String name) {
        boolean is_found = false;
        for (String dict : dictionaries) {
            if (dict.equals(name)) {
                is_found = true;
                break;
            }
        }
        if (is_found) {
            dictionaries.remove(name);
            deleteDictionary(ctx, name);
            return true;
        } else {
            return false;
        }
    }

    public static Library load (Context ctx) {
        // get root directory
        String root_path;
        File root_dir = ctx.getExternalFilesDir(null);
        if (root_dir != null) {
            root_path = root_dir.getAbsolutePath();
        } else {
            Log.e(TAG, "root directory not found");
            return null;
        }
        // make sure directory exists
        File dir = new File(root_path);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                Log.e(TAG, "create root directory error");
                return null;
            }
        }
        // search all files
        Library lib = new Library();
        for (File file : dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+.json");
            }
        })) {
            if (file != null) {
                String filename = file.getName().replaceAll(".json", "");
                lib.dictionaries.add(filename);
            }
        }
        return lib;
    }

    public static void saveDictionary (Context ctx, String name) {
         // create blank dictionary
        Dictionary dict = new Dictionary();
        dict.name = name;
        Dictionary.save(ctx, dict);
    }

    public static void deleteDictionary (Context ctx, String name) {
        // get root directory
        String root_path;
        File root_dir = ctx.getExternalFilesDir(null);
        if (root_dir != null) {
            root_path = root_dir.getAbsolutePath();
        } else {
            Log.e(TAG, "root directory not found");
            return;
        }
        // make sure directory exists
        File dir = new File(root_path);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                Log.e(TAG, "create root directory error");
                return;
            }
        }
        // search all files
        for (File file : dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+.json");
            }
        })) {
            if (file != null) {
                String filename = file.getName().replaceAll(".json", "");
                if (filename.equals(name)) {
                    file.delete();
                }
            }
        }
    }

}
