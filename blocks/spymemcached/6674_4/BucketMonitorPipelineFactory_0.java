package net.spy.memcached.vbucket;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;

import org.jboss.netty.channel.ChannelFactory;
import net.spy.memcached.vbucket.config.Bucket;
import net.spy.memcached.vbucket.config.ConfigurationParser;

public class BucketMonitor extends Observable {

    private final URI cometStreamURI;
    private Bucket bucket;
    private final String httpUser;
    private final String httpPass;
    private final ChannelFactory factory;
    private Channel channel;
    private final String host;
    private final int port;
    private ConfigurationParser configParser;
    private BucketUpdateResponseHandler handler;
    public static final String CLIENT_SPEC_VER = "1.0";

    public BucketMonitor(URI cometStreamURI,  String bucketname, String username, String password, ConfigurationParser configParser) {
        super();
        if (cometStreamURI == null) {
            throw new IllegalArgumentException("cometStreamURI cannot be NULL");
        }
        String scheme = cometStreamURI.getScheme() == null ? "http" : cometStreamURI.getScheme();
        if (!scheme.equals("http")) {
            throw new UnsupportedOperationException("Only http is supported.");
        }

        this.cometStreamURI = cometStreamURI;
        this.httpUser = username;
        this.httpPass = password;
        this.configParser = configParser;
        this.host = cometStreamURI.getHost();
        this.port = cometStreamURI.getPort() == -1 ? 80 : cometStreamURI.getPort();
        factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    }

    public void startMonitor() {
        if (channel != null) {
            Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
                    "Bucket monitor is already started.");
            return;
        }
        createChannel();
        this.handler = channel.getPipeline().get(BucketUpdateResponseHandler.class);
        handler.setBucketMonitor(this);
        HttpRequest request = prepareRequest(cometStreamURI, host);
        channel.write(request);
        try {
            String response = this.handler.getLastResponse();
            logFiner("Getting server list returns this last chunked response:\n" + response);
            Bucket bucket = this.configParser.parseBucket(response);
            setBucket(bucket);
        } catch (ParseException ex) {
            Logger.getLogger(BucketMonitor.class.getName()).log(Level.WARNING,
                    "Invalid client configuration received from server.  Staying with existing configuration.", ex);
            Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINE,
                    "Invalid client configuration received:\n" + handler.getLastResponse() + "\n");
        }
    }

    protected void createChannel() {
        ClientBootstrap bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new BucketMonitorPipelineFactory());

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        channel = future.awaitUninterruptibly().getChannel();
        if (!future.isSuccess()) {
            bootstrap.releaseExternalResources();
            throw new ConnectionException("Could not connect to any pool member.");
        }
        assert(channel != null);
    }

    protected HttpRequest prepareRequest(URI uri, String host) {
        HttpRequest request = new DefaultHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
        request.setHeader(HttpHeaders.Names.HOST, host);
        if (getHttpUser() != null) {
            request.setHeader(HttpHeaders.Names.AUTHORIZATION, getAuthHeader());
        }
        request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);  // No keep-alives for this
        request.setHeader(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_CACHE);
        request.setHeader(HttpHeaders.Names.ACCEPT, "application/json");
        request.setHeader(HttpHeaders.Names.USER_AGENT, "spymemcached vbucket client");
        request.setHeader("X-memcachekv-Store-Client-Specification-Version", CLIENT_SPEC_VER);
        return request;
    }

    private void setBucket(Bucket bucket) {
        if (this.bucket == null || !this.bucket.equals(bucket)) {
            this.bucket = bucket;
            setChanged();
            notifyObservers(this.bucket);
        }
    }

    private String getAuthHeader() {
        StringBuffer clearText = new StringBuffer(getHttpUser());
        clearText.append(':');
        if (getHttpPass() != null) {
            clearText.append(getHttpPass());
        }
        String encodedText = org.apache.commons.codec.binary.Base64.encodeBase64String(clearText.toString().getBytes());
        char[] encodedWoNewline = new char[encodedText.length() - 2];
        encodedText.getChars(0, encodedText.length() - 2, encodedWoNewline, 0);
        String authVal = "Basic " + new String(encodedWoNewline);

        return authVal;
    }

    public String getHttpUser() {
        return httpUser;
    }

    public String getHttpPass() {
        return httpPass;
    }

    private void logFiner(String msg) {
        Logger.getLogger(BucketMonitor.class.getName()).log(Level.FINER, msg);
    }

    public void shutdown() {
        shutdown(-1, TimeUnit.MILLISECONDS);
    }

    public void shutdown(long timeout, TimeUnit unit) {
        deleteObservers();
        if (channel != null) {
            channel.close().awaitUninterruptibly(timeout, unit);
        }
        factory.releaseExternalResources();
    }

    protected void invalidate() {
        try {
            String response = handler.getLastResponse();
            Bucket bucket = this.configParser.parseBucket(response);
            setBucket(bucket);
        } catch (ParseException e) {
            Logger.getLogger(BucketMonitor.class.getName()).log(Level.SEVERE,
                    "Invalid client configuration received from server.  Staying with existing configuration.", e);
        }
    }

    public void setConfigParser(ConfigurationParser configParser) {
        this.configParser = configParser;
    }
}
