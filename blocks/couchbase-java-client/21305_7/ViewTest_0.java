    docUri = "/default/_design/" + TestingClient.MODE_PREFIX
        + DESIGN_DOC_BINARY;
    view = "{\"language\":\"javascript\",\"views\":{\""
        + VIEW_NAME_BINARY + "\":{\"map\":\"function (doc, meta) "
        +"{ if(meta.id.match(/nonjson/)) { emit(meta.id, null); }}\"}}}";
    client.asyncHttpPut(docUri, view);

