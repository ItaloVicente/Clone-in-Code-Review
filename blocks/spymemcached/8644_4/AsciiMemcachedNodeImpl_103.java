        optimizedOp.initialize();
        assert optimizedOp.getState() == OperationState.WRITING;
        ProxyCallback pcb = (ProxyCallback) og.getCallback();
        getLogger().debug("Set up %s with %s keys and %s callbacks", this,
            pcb.numKeys(), pcb.numCallbacks());
      }
    }
  }
