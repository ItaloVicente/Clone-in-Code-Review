      if (binaryConnection.get() == null) {
        bootstrap();
      } else {
        try {
          List<String> configs = getConfigsFromBinaryConnection(binaryConnection.get());
          if (configs.isEmpty()) {
