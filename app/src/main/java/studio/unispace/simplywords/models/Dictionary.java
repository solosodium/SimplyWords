package studio.unispace.simplywords.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by haof on 7/21/2016.
 */
public class Dictionary {

    /**
     * static variables
     */

    private static final String TAG = "Dictionary.java";

    private static final String FILE_NAME = "dictionary.json";

    /**
     * Class Dictionay
     */

    public List<Word> words;

    public Dictionary () {
        words = new LinkedList<>();
    }

    public void addWord (Word w) {
        words.add(0, w);    // add to the top of the list (LIFO)
    }

    public void deleteWord (int idx) {
        words.remove(idx);
    }

    /**
     * static functions
     */

    public static void save (Context ctx, Dictionary dict) {
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
        // prepare data
        Gson gson = new Gson();
        String serialized = gson.toJson(dict);
        byte[] bytes;
        if (Charset.availableCharsets().containsKey("UTF-8")) {
            bytes = serialized.getBytes(Charset.forName("UTF-8"));
        } else {
            bytes = serialized.getBytes(Charset.defaultCharset());
        }
        File file = new File(root_path + "/" + FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "write json file error");
            e.printStackTrace();
        }
    }

    public static Dictionary load (Context ctx) {
        // get root directory
        String root_path;
        File root_dir = ctx.getExternalFilesDir(null);
        if (root_dir != null) {
            root_path = root_dir.getAbsolutePath();
        } else {
            Log.e(TAG, "root directory not found");
            return null;
        }
        // check file exist
        File file = new File(root_path + "/" + FILE_NAME);
        if (file.exists()) {
            // read file
            StringBuffer sb = new StringBuffer("");
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int n;
                while ((n = fis.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, n, "UTF-8"));
                }
            } catch (IOException e) {
                Log.e(TAG, "read json file error");
                e.printStackTrace();
            }
            // GSON
            Gson gson = new Gson();
            return gson.fromJson(sb.toString(), Dictionary.class);
        }
        else {
            // create empty file
            Dictionary dict = new Dictionary();
            // save dictionary
            save(ctx, dict);
            // return
            return dict;
        }
    }

    /**
     * unit testing
     */

    public static void Testing (Context ctx) {
        Dictionary dict = new Dictionary();
        Word w = new Word();
        w.word = "ni ma";
        dict.addWord(w);
        Dictionary.save(ctx, dict);
        Dictionary dict2 = Dictionary.load(ctx);
        Log.d(TAG, dict2.words.get(0).word);
    }

}
