        rv = m.getNewValue(current);
        if (client.cas(key, casval.getCas(), rv, transcoder)
            == CASResponse.OK) {
          done = true;
        }
      } else {
        if (initial == null) {
          done = true;
          rv = null;
        } else if (client.add(key, initialExp, initial, transcoder).get()) {
          done = true;
          rv = initial;
        }
      }
    }
    if (!done) {
      throw new RuntimeException("Couldn't get a CAS in " + max + " attempts");
    }
