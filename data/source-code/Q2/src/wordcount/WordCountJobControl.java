package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountJobControl {

public static enum Wordcounter {
      UniqueWordsInOneDoc
    };
    
public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, " word count ");
    job.setJarByClass(WordCountJobControl.class);
    job.setMapperClass(WordCountMapper.class);
    job.setCombinerClass(WordCountCombiner.class);
    job.setReducerClass(WordCountReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args [0]));
    FileOutputFormat.setOutputPath(job, new Path(args [1]));
    boolean jobWaitFlag = job.waitForCompletion(true);

        Counters c = job.getCounters();
        Counter temp = c.findCounter(Wordcounter.UniqueWordsInOneDoc);
        System.out.println("\n\nWords in only one document:\n"+temp.getValue());

        if(jobWaitFlag)
            System.exit(0);
}

}

