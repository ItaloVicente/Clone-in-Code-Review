
package net.spy.memcached.management;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.spy.memcached.compat.SpyObject;

public class JMXMonitor extends SpyObject {

  private final MBeanServer mbs;
  private final String clientStatsName = "net.spy.memcached:type=Clients";
  private final String operationStatsName =
    "net.spy.memcached:type=Operations";
  private final String errorStatsName = "net.spy.memcached:type=Errors";
  private final ClientStatsImpl clientsmxbean;
  private final OperationStatsImpl operationsmxbean;
  private final ErrorStatsImpl errorsmxbean;

  public JMXMonitor() {
    mbs = ManagementFactory.getPlatformMBeanServer();
    clientsmxbean = new ClientStatsImpl();
    operationsmxbean = new OperationStatsImpl();
    errorsmxbean = new ErrorStatsImpl();
  }

  public boolean enableClientMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(clientStatsName);
      mbs.registerMBean(clientsmxbean, mxbeanName);
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean enableOperationMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(operationStatsName);
      mbs.registerMBean(operationsmxbean, mxbeanName);
      Stats.trackOps = true;
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean enableErrorMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(errorStatsName);
      mbs.registerMBean(errorsmxbean, mxbeanName);
      Stats.trackErrors = true;
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean disableClientMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(clientStatsName);
      mbs.unregisterMBean(mxbeanName);
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean disableOperationMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(operationStatsName);
      mbs.unregisterMBean(mxbeanName);
      Stats.trackOps = false;
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean disableErrorMonitoring() {
    try {
      ObjectName mxbeanName = new ObjectName(errorStatsName);
      mbs.unregisterMBean(mxbeanName);
      Stats.trackErrors = false;
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      return false;
    }
    return true;
  }
}
