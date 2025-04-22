      public long getLong(int index) {
          Object found = content.get(index);
          if (found == null) {
              throw new NullPointerException();
          }
          if (found instanceof Integer) {
              return (Integer) found;
          }
          return (Long) found;
