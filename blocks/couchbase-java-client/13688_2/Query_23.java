
package com.couchbase.client.protocol.views;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.internal.HttpFuture;

import java.util.Iterator;

import net.spy.memcached.compat.SpyObject;

public class Paginator extends SpyObject
  implements Iterator<ViewRow> {

  private static final int MIN_RESULTS = 15;

  private final CouchbaseClient client;
  private final Query query;
  private final View view;
  private final int docsPerPage;

  private ViewResponse page;
  private Iterator<ViewRow> pageItr;
  private ViewRow lastRow;
  private int rowsIterated;

  public Paginator(CouchbaseClient client, View view, Query query,
      int numDocs) {
    this.client = client;
    this.view = view;
    this.query = query.copy();
    this.docsPerPage = (MIN_RESULTS > numDocs) ? MIN_RESULTS : numDocs;
    getNextPage(this.query.setLimit(docsPerPage + 1));
  }

  @Override
  public boolean hasNext() {
    if (!pageItr.hasNext() && page.size() < docsPerPage) {
      return false;
    } else if (!(rowsIterated < docsPerPage)) {
      lastRow = pageItr.next();
      query.setStartkeyDocID(lastRow.getId());
      query.setRangeStart(lastRow.getKey());
      getNextPage(query);
    }
    return true;
  }

  @Override
  public ViewRow next() {
    if (rowsIterated <= docsPerPage) {
      rowsIterated++;
      lastRow = pageItr.next();
      return lastRow;
    }
    return null;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is unsupported");
  }

  private HttpFuture<ViewResponse> getNextPage(Query q) {
    if (query.willReduce()) {
      throw new RuntimeException("Pagination is not supported for reduced"
          + " views");
    }
    page = client.query(view, q);
    pageItr = page.iterator();
    rowsIterated = 0;
    return null;
  }
}
