  private AuthType determineAuthType(String types) {
    if(types.contains("CRAM-MD5")) {
      return AuthType.CRAM_MD5;
    } else if(types.contains("PLAIN")) {
      return AuthType.PLAIN;
    } else {
      getLogger().warn("Received unknown SASL auth mechanism: " + types);
    }
    return null;
  }
