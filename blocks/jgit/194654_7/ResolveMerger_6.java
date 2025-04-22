  protected void addToCheckout(String path
      Attributes[] attributes)
      throws IOException {
    String cleanupSmudgeCommand = tw.getSmudgeCommand(attributes[T_OURS]);
    String checkoutSmudgeCommand = tw.getSmudgeCommand(attributes[T_THEIRS]);
    ioHandler.addToCheckout(path
        attributes[T_THEIRS]
  }

