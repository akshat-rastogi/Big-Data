package wordcount;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer <Text, IntWritable, Text, IntWritable> {

    public static Map<String, Integer> countMap = new HashMap<>();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        //context.write(key, new IntWritable(sum));
        countMap.put(key.toString(), sum);
    }
    
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {        
        Map<String, Integer> sortedMap = new LinkedHashMap();
                
        countMap.entrySet() 
                .stream() 
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        
        int counter = 0;
        for (String key: sortedMap.keySet()) {
            context.write(new Text(key), new IntWritable(sortedMap.get(key)));
            counter ++;
            if(counter == 5) return;
        }
    }
}
