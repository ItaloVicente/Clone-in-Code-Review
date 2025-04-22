  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 4) {
      System.err
          .println("Usage example:\nQuickAuthLoad user password"
              + " localhost:11212 10000");
      System.exit(1);
    }
    SASLConnectReconnect m =
        new SASLConnectReconnect(args[0], args[1], args[2]);
