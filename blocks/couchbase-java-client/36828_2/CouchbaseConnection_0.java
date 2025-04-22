    if (needsRecheckConfigUpdate) {
      getLogger().warn(
        "Node expected to receive data is inactive. This could be due to "
          + "a failure within the cluster. Will check for updated "
          + "configuration. Key without a configured node is: %s.", key);
      cf.checkConfigUpdate();
    }

