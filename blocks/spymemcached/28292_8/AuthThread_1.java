    String[] supportedMechs;
    if (authDescriptor.getMechs() == null
      || authDescriptor.getMechs().length == 0) {
      supportedMechs = listSupportedSASLMechanisms(done);
    } else {
      supportedMechs = authDescriptor.getMechs();
    }

    OperationStatus priorStatus = null;
