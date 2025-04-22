
package com.couchbase.client.vbucket.provider;

import com.couchbase.client.CouchbaseConnection;
import com.couchbase.client.CouchbaseConnectionFactory;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.FailureMode;
import net.spy.memcached.OperationFactory;
import net.spy.memcached.compat.log.Level;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;

public class CouchbaseConfigConnection extends CouchbaseConnection {

  private static final Logger LOGGER = new LoggerProxy(LoggerFactory.getLogger(
    CouchbaseConfigConnection.class));;

  public CouchbaseConfigConnection(int bufSize, CouchbaseConnectionFactory f,
    List<InetSocketAddress> a, Collection<ConnectionObserver> obs,
    FailureMode fm, OperationFactory opfactory) throws IOException {
    super(bufSize, f, a, obs, fm, opfactory);
  }

  @Override
  protected Logger getLogger() {
    return LOGGER;
  }

  public static class LoggerProxy implements Logger {

    private final Logger logger;

    public LoggerProxy(Logger logger) {
      this.logger = logger;
    }

    @Override
    public String getName() {
      return logger.getName();
    }

    @Override
    public boolean isDebugEnabled() {
      return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
      return logger.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
      return logger.isTraceEnabled();
    }

    @Override
    public void log(Level level, Object o, Throwable throwable) {
      logger.log(Level.DEBUG, o, throwable);
    }

    @Override
    public void log(Level level, Object o) {
      logger.log(Level.DEBUG, o);
    }

    @Override
    public void trace(Object o, Throwable throwable) {
      logger.trace(o, throwable);
    }

    @Override
    public void trace(Object o) {
      logger.trace(o);
    }

    @Override
    public void trace(String s, Object... objects) {
      logger.trace(s, objects);
    }

    @Override
    public void debug(Object o, Throwable throwable) {
      logger.debug(o, throwable);
    }

    @Override
    public void debug(Object o) {
      logger.debug(o);
    }

    @Override
    public void debug(String s, Object... objects) {
      logger.debug(s, objects);
    }

    @Override
    public void info(Object o, Throwable throwable) {
      logger.debug(o, throwable);
    }

    @Override
    public void info(Object o) {
      logger.debug(o);
    }

    @Override
    public void info(String s, Object... objects) {
      logger.debug(s, objects);
    }

    @Override
    public void warn(Object o, Throwable throwable) {
      logger.debug(o, throwable);
    }

    @Override
    public void warn(Object o) {
      logger.debug(o);
    }

    @Override
    public void warn(String s, Object... objects) {
      logger.debug(s, objects);
    }

    @Override
    public void error(Object o, Throwable throwable) {
      logger.debug(o, throwable);
    }

    @Override
    public void error(Object o) {
      logger.debug(o);
    }

    @Override
    public void error(String s, Object... objects) {
      logger.debug(s, objects);
    }

    @Override
    public void fatal(Object o, Throwable throwable) {
      logger.debug(o, throwable);
    }

    @Override
    public void fatal(Object o) {
      logger.debug(o);
    }

    @Override
    public void fatal(String s, Object... objects) {
      logger.debug(s, objects);
    }
  }

}
