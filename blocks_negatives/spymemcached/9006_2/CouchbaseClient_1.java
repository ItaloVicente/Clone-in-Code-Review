    String queryString = query.toString();
    String params = (queryString.length() > 0) ? "&reduce=false"
        : "?reduce=false";
    params += "&include_docs=false";

    String uri = view.getURI() + queryString + params;
