
package com.couchbase.client.protocol.views;

import java.util.Collection;
import java.util.Map;

public class ViewResponseReduced extends ViewResponse {

  public ViewResponseReduced(final Collection<ViewRow> rows,
      final Collection<RowError> errors) {
    super(rows, errors);
  }

  @Override
  public Map<String, Object> getMap() {
    throw new UnsupportedOperationException("This view doesn't contain "
        + "documents");
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (ViewRow r : rows) {
      s.append(r.getKey());
      s.append(" : ");
      s.append(r.getValue());
      s.append("\n");
    }
    return s.toString();
  }
}
