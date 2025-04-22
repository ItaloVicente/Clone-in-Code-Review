
package com.couchbase.client.protocol.views;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public abstract class ViewResponse implements Iterable<ViewRow> {
  protected final Collection<ViewRow> rows;
  protected final Collection<RowError> errors;

  public ViewResponse(final Collection<ViewRow> r,
      final Collection<RowError> e) {
    rows = r;
    errors = e;
  }

  public Collection<RowError> getErrors() {
    return errors;
  }

  @Override
  public Iterator<ViewRow> iterator() {
    return rows.iterator();
  }

  public int size() {
    return rows.size();
  }

  public abstract Map<String, Object> getMap();
}
