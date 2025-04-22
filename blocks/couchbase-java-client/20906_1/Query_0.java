    } else if (key.equals(KEY)) {
      if (value instanceof Integer) {
        return key + "=" + value.toString();
      } else {
        return key + "=\"" + value + "\"";
      }
    }
      else if (value instanceof Stale) {
