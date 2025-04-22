    docUri = "/default/_design/" + TestingClient.MODE_PREFIX
        + DESIGN_DOC_OBSERVE;
    view = "{\"language\":\"javascript\",\"views\":{\""
        + VIEW_NAME_OBSERVE + "\":{\"map\":\"function (doc, meta) {"
        + " if(doc.type == \\\"observetest\\\") { emit(meta.id, null); } }\"}}}";
    c.asyncHttpPut(docUri, view);

