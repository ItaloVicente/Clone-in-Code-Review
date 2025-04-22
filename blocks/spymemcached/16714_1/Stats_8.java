
package net.spy.memcached.management;

public interface OperationStatsMXBean {
  long getTotalOps();
  long getTotalAdd();
  long getTotalAppend();
  long getTotalCas();
  long getTotalDecr();
  long getTotalDelete();
  long getTotalGet();
  long getTotalSet();
  long getTotalStats();
  long getTotalReplace();

  void reset();
  void disable();
  void enable();
}
