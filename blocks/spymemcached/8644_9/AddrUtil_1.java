      int finalColon = hoststuff.lastIndexOf(':');
      if (finalColon < 1) {
        throw new IllegalArgumentException("Invalid server ``" + hoststuff
            + "'' in list:  " + s);
      }
      String hostPart = hoststuff.substring(0, finalColon);
      String portNum = hoststuff.substring(finalColon + 1);
