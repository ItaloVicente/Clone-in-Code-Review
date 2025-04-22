      OperationStatus status = foundStatus.get();
      if (status != null && !status.isSuccess()) {
        getLogger().warn(host() + " SASL Step failed, retrying: " + foundStatus.get());
        continue;
      }

