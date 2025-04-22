    if (op.getCloneCount() >= MAX_CLONE_COUNT) {
      getLogger().warn("Cancelling operation " + op + "because it has been "
        + "retried (cloned) more than " + MAX_CLONE_COUNT + "times.");
      op.cancel();
      return;
    }

