    if (args.containsKey(KEYS)) {
      query.setKeys((String)args.get(KEYS));
    }
    if (args.containsKey(LIMIT)) {
      query.setLimit(((Integer)args.get(LIMIT)).intValue());
    }
    if (args.containsKey(REDUCE)) {
      query.setReduce(((Boolean)args.get(REDUCE)).booleanValue());
    }
    if (args.containsKey(SKIP)) {
      query.setSkip(((Integer)args.get(SKIP)).intValue());
    }
    if (args.containsKey(STALE)) {
      query.setStale((Stale)args.get(STALE));
    }
    if (args.containsKey(STARTKEY)) {
      query.setRangeStart((String)args.get(STARTKEY));
    }
    if (args.containsKey(STARTKEYDOCID)) {
      query.setStartkeyDocID((String)args.get(STARTKEYDOCID));
    }
    if (args.containsKey(ONERROR)) {
      query.setOnError((OnError)args.get(ONERROR));
    }
    if (args.containsKey(BBOX)) {
      String[] bbox = bboxSplit.split((CharSequence) args.get(BBOX));
      query.setBbox(Double.parseDouble(bbox[0]), Double.parseDouble(bbox[1]),
        Double.parseDouble(bbox[2]), Double.parseDouble(bbox[3]));
    }
    if (args.containsKey(DEBUG)) {
      query.setDebug(((Boolean)args.get(DEBUG)).booleanValue());
    }
    query.setIncludeDocs(willIncludeDocs());

    return query;
