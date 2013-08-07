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

public class KafkaTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws Exception {
		
	  TopologyBuilder builder = new TopologyBuilder();
      
//      SpoutConfig spoutConf = new SpoutConfig(
//    		  StaticHosts.fromHostString(ImmutableList.of("192.168.2.253:9092"),1),
//    		  "test","/kafkastorm","discovery");
      
      SpoutConfig spoutConf = new SpoutConfig(
    		  StaticHosts.fromHostString(ImmutableList.of("192.168.2.251:9092"),1),
    		  "test","/kafkastorm","discovery");
      
      spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
//      spoutConf.forceStartOffsetTime(-2);
//      spoutConf.zkServers = new ArrayList<String>() {{
//          add("192.168.2.253");
//      }};
//      spoutConf.zkPort = 2181;
      
      KafkaSpout kafkaSpout = new KafkaSpout(spoutConf);
      builder.setSpout("spout", kafkaSpout, 3);
      
      builder.setBolt("split", new SplitSentenceBolt(), 8)
      .shuffleGrouping("spout");
      
      builder.setBolt("count", new WordCountBolt(), 12).fieldsGrouping(
				"split", new Fields("word"));
      
      Config conf = new Config();
      //conf.setDebug(true);

      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("kafka-test", conf, builder.createTopology());
      Thread.sleep(3000);
	  //cluster.shutdown();
	
//		Properties props = new Properties();
//		props.put("zk.connect", "192.168.2.253:2181");
//		props.put("zk.connectiontimeout.ms", "1000000");
//		props.put("serializer.class", "kafka.serializer.StringEncoder");
//		props.put("groupid", "test_group2");

//154 storm
//例子程序      
//storm  安装包
//文档、网页
//手册 
//reds、hbase
      
      
	}

}
