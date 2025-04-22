    if (!(opFact instanceof BinaryOperationFactory) && (def != 0 || exp != -1)) {
      throw new UnsupportedOperationException("Default value or expiration "
        + "time are not supported on the async mutate methods. Use either the "
        + "binary protocol or the sync variant.");
    }

