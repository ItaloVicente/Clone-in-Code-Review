      nodesToShutdown.addAll(oddNodes);
    } catch (IOException e) {
      getLogger().error("Connection reconfiguration failed", e);
    } finally {
      reconfiguring = false;
    }
  }
