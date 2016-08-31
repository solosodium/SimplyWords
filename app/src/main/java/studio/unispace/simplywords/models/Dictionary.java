package studio.unispace.simplywords.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    /**
     * Class Dictionay
     */

    public String dictName;
    public List<Word> words;

    public Dictionary (String name) {
        dictName = name;
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
        String serialized = gson.toJson(dict, Dictionary.class);
        Charset charset = Charset.availableCharsets().containsKey("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset();
        File file = new File(root_path + "/" + dict.dictName + ".json");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(serialized);
            bw.close();
            osw.close();
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "write json file error");
            e.printStackTrace();
        }
    }

    public static Dictionary load (Context ctx, String name) {
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
        File file = new File(root_path + "/" + name + ".json");
        if (file.exists()) {
            // read file
            Charset charset = Charset.availableCharsets().containsKey("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset();
            StringBuffer sb = new StringBuffer("");
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, charset);
                BufferedReader br = new BufferedReader(isr);
                String line;
                line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                br.close();
                isr.close();
                fis.close();
            } catch (IOException e) {
                Log.e(TAG, "read json file error");
                e.printStackTrace();
            }
            // GSON
            Gson gson = new Gson();
            Dictionary result = gson.fromJson(sb.toString(), Dictionary.class);
            // change name if necessary
            if (!result.dictName.equals(name)) {
                result.dictName = name;
                save(ctx, result);
            }
            // return
            return result;
        }
        else {
            // create empty file
            Dictionary dict = new Dictionary(name);
            // save dictionary
            save(ctx, dict);
            // return
            return dict;
        }
    }

}
