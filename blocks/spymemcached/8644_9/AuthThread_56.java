      priorStatus = foundStatus.get();
      if (priorStatus != null) {
        if (!priorStatus.isSuccess()) {
          getLogger().warn(
              "Authentication failed to " + node.getSocketAddress());
        }
      }
    }
    return;
  }
