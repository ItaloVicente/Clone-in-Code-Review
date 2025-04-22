      if(e.getCause() instanceof CancellationException) {
        throw (CancellationException) e.getCause();
      } else {
        throw new RuntimeException("Exception waiting for value", e);
      }
    } catch (TimeoutException e) {
      throw new OperationTimeoutException("Timeout waiting for value: ", e);
