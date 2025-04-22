  public Query copy() {
    Query query = new Query();

    if (args.containsKey(DESCENDING)) {
      query.setDescending(((Boolean)args.get(DESCENDING)).booleanValue());
    }
    if (args.containsKey(ENDKEY)) {
      query.setRangeEnd((String)args.get(ENDKEY));
    }
    if (args.containsKey(ENDKEYDOCID)) {
      query.setEndkeyDocID((String)args.get(ENDKEYDOCID));
    }
    if (args.containsKey(GROUP)) {
      query.setGroup(((Boolean)args.get(GROUP)).booleanValue());
    }
    if (args.containsKey(GROUPLEVEL)) {
      query.setGroupLevel(((Integer)args.get(GROUPLEVEL)).intValue());
