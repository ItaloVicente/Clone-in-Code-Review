public class ViewConnection extends SpyObject implements
  Reconfigurable {
  private static final int NUM_CONNS = 1;
  private static final int MAX_ADDOP_RETRIES = 6;

  private volatile boolean shutDown = false;
  protected volatile boolean reconfiguring = false;
  protected volatile boolean running = true;
