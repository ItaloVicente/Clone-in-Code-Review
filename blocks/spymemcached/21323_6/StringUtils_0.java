    if(!binary) {
      for (byte b : keyBytes) {
        if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
          throw new IllegalArgumentException(
              "Key contains invalid characters:  ``" + key + "''");
        }
