package com.edu.bupt.mr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class KafkaSpoutTest implements IRichSpout{

    private ConsumerConnector consumerConnector;
    private ConsumerIterator<byte[], byte[]> it;
    SpoutOutputCollector _collector;
    
    public KafkaSpoutTest(final Properties kafkaProperties, final String topic) {

    }

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
		
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.2.253:2181");
		props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
		props.put("group.id", "test_group");
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
				
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	    topicCountMap.put("test", new Integer(2));
	    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
	    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("test");
	    KafkaStream stream = streams.get(0);
	    it = stream.iterator();
	    
	}

	@Override
	public void close() {
		if (consumerConnector != null) {
            consumerConnector.shutdown();
        }
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTuple() {
		Utils.sleep(100);
		while (it.hasNext()){
			String msg = new String(it.next().message());
			System.out.println(msg);
			_collector.emit(new Values(msg));
		}
		
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("kafka"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
