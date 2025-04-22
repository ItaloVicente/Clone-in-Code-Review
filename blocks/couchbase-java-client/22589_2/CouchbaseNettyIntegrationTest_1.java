
package com.couchbase.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import net.spy.memcached.TestConfig;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.Test;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class CouchbaseNettyIntegrationTest {

  private final int port = 8890;

  @Test
  public void testNettyIntegration() throws Exception {
    bootstrapServer();

    ClientBootstrap cb = new ClientBootstrap(new NioClientSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()
    ));

    cb.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        return Channels.pipeline(new StringDecoder(), new StringEncoder());
      }
    });

    ChannelFuture future = cb.connect(new InetSocketAddress("localhost", port));
    Channel channel = future.awaitUninterruptibly().getChannel();
    assertTrue(future.isSuccess());

    channel.write("Hello World");
  }

  private void bootstrapServer() {
    ServerBootstrap bootstrap = new ServerBootstrap(
      new NioServerSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool()
      )
    );

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        return Channels.pipeline(new EchoServerHandler());
      }
    });
    bootstrap.bind(new InetSocketAddress(port));
  }

  class EchoServerHandler extends SimpleChannelUpstreamHandler {
    private CouchbaseClient client;

    public EchoServerHandler() throws IOException {
      this.client = new CouchbaseClient(
        Arrays.asList(URI.create("http://" +  TestConfig.IPV4_ADDR
          + ":8091/pools")),
        "default",
        ""
      );
      this.client.set("received", 0, "foo");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
      System.out.println("Received Message");
      System.out.println(client.get("received"));
    }

  }

}
