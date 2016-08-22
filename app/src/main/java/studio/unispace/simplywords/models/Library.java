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
        File dir = getDir(ctx);
        if (dir == null) {
            return false;
        }
        for (String dict : dictionaries) {
            if (dict.equals(name)) {
                return false;
            }
        }
        // create blank dictionary
        Dictionary dict = new Dictionary(name);
        dict.dictName = name;
        Dictionary.save(ctx, dict);
        // update
        dictionaries.add(name);
        return true;
    }

    public boolean removeDictionary (Context ctx, String name) {
        File dir = getDir(ctx);
        if (dir == null) {
            return false;
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
                    dictionaries.remove(name);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean renameDictionary (Context ctx, String oldName, String newName) {
        File dir = getDir(ctx);
        if (dir == null) {
            return false;
        }
        // search duplicate
        for (File file : dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+.json");
            }
        })) {
            if (file != null) {
                String filename = file.getName().replaceAll(".json", "");
                if (filename.equals(newName)) {
                    return false;
                }
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
                if (filename.equals(oldName)) {
                    File in = new File(dir, file.getName());
                    File out = new File(dir, newName + ".json");
                    if (in.renameTo(out)) {
                        dictionaries.remove(oldName);
                        dictionaries.add(newName);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    //
    // public static function for initialization
    //

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

    private File getDir (Context ctx) {
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
        return dir;
    }

}
