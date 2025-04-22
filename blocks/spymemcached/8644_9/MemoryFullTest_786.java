  public static void main(String[] args) throws Exception {
    try {
      assert false;
      throw new RuntimeException("Assertions not enabled.");
    } catch (AssertionError e) {
    }
