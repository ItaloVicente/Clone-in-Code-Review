 * The Paginator allows easy pagination over a ViewResult.
 *
 * It is recommended not to instantiate this object directly, but to obtain
 * a reference through the CouchbaseClient object like this:
 *
 *   View view = client.getView("design_doc", "viewname");
 *   Query query = new Query();
 *   int docsPerPage = 15;
 *   Paginator paginator = client.paginatedQuery(view, query, docsPerPage);
 *
