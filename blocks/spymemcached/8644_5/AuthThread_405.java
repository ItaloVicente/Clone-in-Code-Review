  private Operation buildOperation(OperationStatus st, OperationCallback cb) {
    if (st == null) {
      return opFact.saslAuth(authDescriptor.getMechs(), node.getSocketAddress()
          .toString(), null, authDescriptor.getCallback(), cb);
    } else {
      return opFact.saslStep(authDescriptor.getMechs(), KeyUtil.getKeyBytes(st
          .getMessage()), node.getSocketAddress().toString(), null,
          authDescriptor.getCallback(), cb);
    }
  }
