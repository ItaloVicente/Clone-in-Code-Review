      String argument;
      try {
        argument = arg.getKey() + "=" + prepareValue(
          arg.getKey(), arg.getValue()
        );
      } catch (Exception ex) {
        throw new RuntimeException("Could not prepare view argument: " + ex);
      }
      result.append(argument);
