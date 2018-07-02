package wordcount;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordCountMapper extends Mapper<Object, Text, Text, Text> {
    private Text word = new Text();

    private String tokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@]";


    public static String fileName = new String();
    protected void setup(Context context) throws java.io.IOException, java.lang.InterruptedException
    {
        fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String cleanLine = value.toString()
                .replaceAll("--"," ")
                .replaceAll("[-—‘’”“•´]","")
                .replaceAll(tokens, " ")
                .toLowerCase();
        
        String[] wordsArray = cleanLine.split("\\s");
        
        for (int i=0;i<wordsArray.length;i++) {
            if(!wordsArray[i].trim().equals("")){
                word.set(wordsArray[i].trim());
                context.write(word, new Text(fileName));
            }
        }
    }
}
