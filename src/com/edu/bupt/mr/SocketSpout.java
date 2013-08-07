package com.edu.bupt.mr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
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

public class SocketSpout implements IRichSpout{
	private ServerSocket server;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
    private SpoutOutputCollector _collector;
    private BufferedReader reader;
    
	@Override
	public void open(Map conf, TopologyContext context,
	SpoutOutputCollector collector) {
		System.out.println("open");
		_collector = collector;
		
//		new Thread() {
//            @Override
//            public void run() {
//                try {
//                    ServerSocket serverSocket = new ServerSocket(27888);
////                    serverSocket.bind(new SocketAddress());
//                    while (true) {
//                    	System.out.println(serverSocket.getInetAddress());
//                        Socket socket = serverSocket.accept();
//                        System.out.println("accept");
//                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    }
//                } catch (IOException e) {
//                	System.out.println("error");
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        }.start();
        
		try {
//            server = new ServerSocket(27888);
            server =new ServerSocket(27888, 1, InetAddress.getByName("192.168.2.114")); 
            Socket connection = server.accept();
            System.out.println("ok");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
        	System.out.println("erorr");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
		
//		System.out.println("open");
//		try {
//			if(server==null){
//				System.out.println("Server is null");
//				server = new ServerSocket(27888);
//			}
//		} catch (IOException e){
//			System.out.println("error 1");
//			try {
//				if(socket!=null){
//					System.out.println("socket is not null");
//					socket.close();
//				}
//				System.out.println("socket is null");
//			} catch (IOException e1) {
//				System.out.println("error 2");
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		} 
	}

	@Override
	public void close() {
		System.out.println("close");
		try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
//		System.out.println("next");
//		if (reader != null) {
//			System.out.println("ok");
//            try {
//            	System.out.println("ss");
//                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//                	System.out.println("d");
//                	System.out.println(line);
////                	String[] columns = line.split(",");
////                    if(columns.length>5){
////                        Values tuple = new Values(columns[0], columns[1], Long.parseLong(columns[2]), columns[4], columns[5]);
//                        _collector.emit(new Values(line));
////                        _collector.emit(SIGNALLING, tuple);
////                    }
////                    if(count++>1000){
////                        break;
////                    }
//                }
//            } catch (IOException e) {
//            	System.out.println("error");
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
		
		while (true) {
            try {
                String packet = reader.readLine();
                System.out.println(packet);
                if (packet == null) continue;
                _collector.emit(new Values(packet));
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
		
//		System.out.println("nextTuple");
//		String line = new String();
//		try {
//			socket=server.accept();
//			System.out.println("dd");
//			in = new BufferedReader(new InputStreamReader(
//					socket.getInputStream()));
//			line = in.readLine();
//			System.out.println("line"+line);
//			out.close();
//			in.close();
//			socket.close();
//		} catch (IOException e) {
//			System.out.println("error");
//			try {
//				socket.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		} 
//		_collector.emit(new Values(line));
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
		declarer.declare(new Fields("string"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
