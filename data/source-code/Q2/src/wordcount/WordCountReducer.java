package wordcount;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer <Text, Text, Text, Text> {
    
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    
        String docID ="";
        for (Text val : values) {
            if(docID.equals(""))
                docID = val.toString();
            else if(!docID.equals(val.toString()))
                return;
        }
        
        context.getCounter(WordCountJobControl.Wordcounter.UniqueWordsInOneDoc).increment(1);
        context.write(key, new Text(docID));
    }

}
