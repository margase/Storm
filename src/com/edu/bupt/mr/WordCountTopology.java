package com.edu.bupt.mr;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("spout", new RandomSentenceSpout(), 5);

		builder.setBolt("split", new SplitSentenceBolt(), 8).shuffleGrouping(
				"spout");

		builder.setBolt("count", new WordCountBolt(), 12).fieldsGrouping(
				"split", new Fields("word"));

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(3);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
			
		} else {
			conf.setMaxTaskParallelism(3);

			LocalCluster cluster = new LocalCluster();

			cluster.submitTopology("word-count", conf, builder.createTopology());

			Thread.sleep(30000);

			cluster.shutdown();
		}

	}

}
