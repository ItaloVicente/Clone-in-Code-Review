    if(components.size() == 1) {
      if(components.get(0) == EMPTY_OBJECT) {
        return new JSONObject().toString();
      } else {
        return components.get(0).toString();
      }
    }

