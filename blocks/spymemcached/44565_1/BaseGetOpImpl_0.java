  
  protected byte[] getAfterKeyBytes() {
    return String.valueOf(exp).getBytes();
  }
  
  protected boolean useAfterKeyBytes() {
    return hasExp;
  }
