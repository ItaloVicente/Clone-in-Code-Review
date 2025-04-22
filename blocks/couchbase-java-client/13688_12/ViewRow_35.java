
package com.couchbase.client.protocol.views;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ViewResponseWithDocs extends ViewResponse {

  private Map<String, Object> map;

  public ViewResponseWithDocs(final Collection<ViewRow> rows,
      final Collection<RowError> errors) {
    super(rows, errors);
    map = null;
  }

  @Override
  public Map<String, Object> getMap() {
    if (map == null) {
      map = new HashMap<String, Object>();
      Iterator<ViewRow> itr = iterator();

      while(itr.hasNext()) {
        ViewRow cur = itr.next();
        map.put(cur.getId(), cur.getDocument());
      }
    }
    return Collections.unmodifiableMap(map);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (ViewRow r : rows) {
      s.append(r.getId());
      s.append(" : ");
      s.append(r.getKey());
      s.append(" : ");
      s.append(r.getValue());
      s.append(" : ");
      s.append(r.getDocument());
      s.append("\n");
    }
    return s.toString();
  }
}
