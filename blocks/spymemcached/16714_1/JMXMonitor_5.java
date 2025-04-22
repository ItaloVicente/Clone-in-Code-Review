
package net.spy.memcached.management;

public interface ErrorStatsMXBean {
  long getSuccessfulOps();
  long getNotFoundErrors();
  long getExistsErrors();
  long getInvalidErrors();
  long getNotStoredErrors();
  long getBadDeltaErrors();
  long getNotMyVbucketErrors();
  long getAuthErrors();
  long getAuthContinueErrors();
  long getUnknownCommandErrors();
  long getNoMemoryErrors();
  long getNotSupportedErrors();
  long getInternalErrors();
  long getBusyErrors();
  long getTemporaryFailureErrors();
  long getUnknownErrorErrors();
  long getCancelledErrors();
  long getTimoutErrors();
  long getExceptionErrors();

  void reset();
  void disable();
  void enable();
}
