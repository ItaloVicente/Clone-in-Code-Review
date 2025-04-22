      ViewResponse response = op.next();
      for (ViewRow row: response) {
        String key = row.getId();
        if (!ITEMS.containsKey(key)) {
          assert false : "Got bad key: " + key + " during pagination";
        }
        count++;
