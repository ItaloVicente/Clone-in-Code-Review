      if ((or[0] != ObserveResponse.FOUND_NOT_PERSISTED)
              && (or[0] != ObserveResponse.NOT_FOUND_NOT_PERSISTED)) {
        masterPersisted = true;
      }
        throw new ObservedModifiedException("Observe - the key was modified on "
                + key);
      }

      for (int i=1; i < or.length; i++) {
        if (or[i] == ObserveResponse.UNINITIALIZED) {
