      try {
        Long.parseLong(value.toString());
        encoded = value.toString();
      } catch(NumberFormatException ex) {
        encoded = "\"" + value.toString() + "\"";
      }
