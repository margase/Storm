package com.edu.bupt.mr;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.ImmutableList;

import storm.kafka.KafkaConfig;
import storm.kafka.KafkaConfig.BrokerHosts;
import storm.kafka.KafkaConfig.StaticHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class SocketTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws Exception {
	  
	  TopologyBuilder builder = new TopologyBuilder();
      
      builder.setSpout("spout",new SocketSpout());
      
      builder.setBolt("split", new SocketBolt(), 8)
      .shuffleGrouping("spout");
      
      Config conf = new Config();

      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("socket-test", conf, builder.createTopology());
      Thread.sleep(3000);
      
	}

}
