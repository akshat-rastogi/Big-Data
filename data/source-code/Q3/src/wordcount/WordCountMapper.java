package wordcount;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper <Object, Text, Text, IntWritable> {
    private Text word = new Text();
    public static String stopword = new String();
    private final static IntWritable one = new IntWritable(1);
    private String tokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@~]";

    @Override
    public void setup(Context context){
        Configuration conf = context.getConfiguration();
        stopword = conf.get("stopword");
        
    }
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String cleanLine = value.toString()
                .replaceAll("--"," ")
                .replaceAll("[-—‘’”“•´]","")
                .replaceAll(tokens, " ")    
                .replaceAll("[\\W]+"," ")
                .toLowerCase();
        
        String[] wordsArray = cleanLine.split(" ");
        
        for (int i=0;i<(wordsArray.length-1);i++) {
            if(wordsArray[i].trim().equalsIgnoreCase(stopword))
            {
                word.set(wordsArray[i+1].trim());
                context.write(word, one);
            }
        }
    }
}