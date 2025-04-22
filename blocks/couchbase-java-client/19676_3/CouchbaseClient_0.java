    assert view != null : "Who passed me a null view";
    assert query != null : "who passed me a null query";
    String viewUri = view.getURI();
    String queryToRun = query.toString();
    assert viewUri != null : "view URI seems to be null";
    assert queryToRun != null  : "query seems to be null";
    String uri = viewUri + queryToRun;
    getLogger().info("lookin for:" + uri);
