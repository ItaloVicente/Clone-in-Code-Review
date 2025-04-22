    if(components.size() == 1 && !forceArray) {
      Object component = components.get(0);
      if(component == EMPTY_OBJECT) {
        return new JSONObject().toString();
      } else if(component instanceof String) {
        return "\"" + component + "\"";
