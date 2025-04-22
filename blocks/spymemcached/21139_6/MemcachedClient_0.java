      if (findNode(sa) != null) {
          authMonitor.authConnection(mconn, opFact, authDescriptor, findNode(sa));
      }
      else {
          getLogger().warn("Connection established Not auth. " + sa + " to " +
              mconn.getLocator().getAll());
      }
