      if (op.getCloneCount() >= MAX_CLONE_COUNT) {
        getLogger().debug("Cancelling operation " + op + "because it has been "
          + "cloned more than " + MAX_CLONE_COUNT + "times.");
        op.cancel();
        return;
      }

