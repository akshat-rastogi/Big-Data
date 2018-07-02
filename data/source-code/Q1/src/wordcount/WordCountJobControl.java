package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountJobControl {

    public static enum Wordcounter {
      DistinctWordsCount,
      ZWordsCount,
      LessThanFourFrequencyCount
    };
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, " word count ");
        job.setJarByClass(WordCountJobControl.class);
        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountCombiner.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args [0]));
        FileOutputFormat.setOutputPath(job, new Path(args [1]));


        boolean jobWaitFlag = job.waitForCompletion(true);

        Counters c = job.getCounters();
        Counter temp;

        temp = c.findCounter(Wordcounter.DistinctWordsCount);
        System.out.println("\n\nCount of total distinct words:\n"+temp.getValue());

        temp = c.findCounter(Wordcounter.ZWordsCount);
        System.out.println("\n\nCount of words starting with Z/z:\n"+temp.getValue());
        temp = c.findCounter(Wordcounter.LessThanFourFrequencyCount);
        System.out.println("\n\nCount of words with less than 4 Frequency:\n"+temp.getValue());

        if(jobWaitFlag)
            System.exit(0);
        
    }

}

