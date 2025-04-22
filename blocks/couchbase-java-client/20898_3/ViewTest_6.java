      ViewResponse response = op.next();
      for(ViewRow row: response) {
        if (count == 5) {
          assert client.delete("key112").get().booleanValue()
              : "Deleteing key key112 failed";
          Thread.sleep(1000);
        }
        count++;
