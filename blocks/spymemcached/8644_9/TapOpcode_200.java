  public static TapOpcode getOpcodeByByte(byte b) {
    if (b == TapOpcode.DELETE.opcode) {
      return TapOpcode.DELETE;
    } else if (b == TapOpcode.FLUSH.opcode) {
      return TapOpcode.DELETE;
    } else if (b == TapOpcode.MUTATION.opcode) {
      return TapOpcode.MUTATION;
    } else if (b == TapOpcode.NOOP.opcode) {
      return TapOpcode.NOOP;
    } else if (b == TapOpcode.OPAQUE.opcode) {
      return TapOpcode.OPAQUE;
    } else if (b == TapOpcode.REQUEST.opcode) {
      return TapOpcode.REQUEST;
    } else if (b == TapOpcode.SASLAUTH.opcode) {
      return TapOpcode.SASLAUTH;
    } else if (b == TapOpcode.SASLLIST.opcode) {
      return TapOpcode.SASLLIST;
    } else if (b == TapOpcode.VBUCKETSET.opcode) {
      return TapOpcode.VBUCKETSET;
    } else {
      return null;
    }
  }
