      ViewResponse response = op.next();
      for (ViewRow row: response) {
        if (!ITEMS.containsKey(row.getId())) {
          assert false : "Got bad key: " + row.getId() + " during pagination";
        }
        count++;
