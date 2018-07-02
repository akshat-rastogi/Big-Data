package wordcount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer <Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();
    
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        context.getCounter(WordCountJobControl.Wordcounter.DistinctWordsCount).increment(1);
        if(key.toString().startsWith("Z") || key.toString().startsWith("z")){
            context.getCounter(WordCountJobControl.Wordcounter.ZWordsCount).increment(1);
        }
        if(sum<4){
            context.getCounter(WordCountJobControl.Wordcounter.LessThanFourFrequencyCount).increment(1);
        }
        result.set(sum);
        context.write(key, result);
    }
}
