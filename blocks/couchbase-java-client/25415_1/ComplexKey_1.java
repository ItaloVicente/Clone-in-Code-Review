      JSONArray key = new JSONArray();
      for (Object component : components) {
        if (component == EMPTY_OBJECT) {
          key.put(new JSONObject());
        } else {
          key.put(component);
        }
