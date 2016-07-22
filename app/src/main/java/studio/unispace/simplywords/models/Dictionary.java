package studio.unispace.simplywords.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by haof on 7/21/2016.
 */
public class Dictionary {

    /**
     * static variables
     */

    public static final String FILE_NAME = "dictionary.json";

    /**
     * Class Dictionay
     */

    public List<Word> words;

    public Dictionary () {
        words = new LinkedList<>();
    }

    public void addWord (Word w) {
        words.add(w);
    }

    /**
     * static functions
     */

    public static void save (Context ctx, Dictionary dict) {
        // prepare data
        Gson gson = new Gson();
        String serialized = gson.toJson(dict);
        byte[] bytes = serialized.getBytes();

        File file = new File(ctx.getFilesDir(), FILE_NAME);
        try {

            FileOutputStream fos = ctx.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(bytes);
            fos.close();

            FileInputStream fis = ctx.openFileInput(FILE_NAME);
            StringBuffer fileContent = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
            Log.d("TEST", fileContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
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
    }

}
