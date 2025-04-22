    String prefix = "/" + bucketName + "/_design/" + designDocumentName;
    if(viewType.equals(ViewType.MAPREDUCE)) {
      return prefix + "/_view/" + viewName;
    } else if(viewType.equals(ViewType.SPATIAL)) {
      return prefix + "/_spatial/" + viewName;
    } else {
      throw new RuntimeException("Unsupported View Type: " + viewType);
    }
