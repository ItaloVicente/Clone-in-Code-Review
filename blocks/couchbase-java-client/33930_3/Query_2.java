  public Query setInclusiveEnd(final boolean inclusiveend) {
    params[PARAM_INCLUSIVEEND_OFFSET] = "inclusive_end";
    params[PARAM_INCLUSIVEEND_OFFSET+1] = Boolean.toString(inclusiveend);
    return this;
  }


  public Query setSkip(final int skip) {
    params[PARAM_SKIP_OFFSET] = "skip";
    params[PARAM_SKIP_OFFSET+1] = Integer.toString(skip);
    return this;
  }

  public Query setStale(final Stale stale) {
    params[PARAM_STALE_OFFSET] = "stale";
    params[PARAM_STALE_OFFSET+1] = stale.toString();
    return this;
  }


  public Query setOnError(final OnError onError) {
    params[PARAM_ONERROR_OFFSET] = "on_error";
    params[PARAM_ONERROR_OFFSET+1] = onError.toString();
    return this;
  }

  public Query setDebug(final boolean debug) {
    params[PARAM_DEBUG_OFFSET] = "debug";
    params[PARAM_DEBUG_OFFSET+1] = Boolean.toString(debug);
    return this;
  }

