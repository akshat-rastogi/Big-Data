package wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    private String tokens = "[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"'—+&%@~]";

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
                context.write(word, one);
            }
        }
    }
}