
package com.couchbase.client.protocol.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.spy.memcached.util.StringUtils;

public class Query {
  private static final String DESCENDING = "descending";
  private static final String ENDKEY = "endkey";
  private static final String ENDKEYDOCID = "endkey_docid";
  private static final String GROUP = "group";
  private static final String GROUPLEVEL = "group_level";
  private static final String INCLUSIVEEND = "inclusive_end";
  private static final String KEY = "key";
  private static final String LIMIT = "limit";
  private static final String REDUCE = "reduce";
  private static final String SKIP = "skip";
  private static final String STALE = "stale";
  private static final String STARTKEY = "startkey";
  private static final String STARTKEYDOCID = "startkey_docid";
  private static final String UPDATESEQ = "update_seq";
  private boolean includedocs = false;

  private Map<String, Object> args;

  public Query() {
    args = new HashMap<String, Object>();
  }

  public boolean willReduce() {
    return (args.containsKey(REDUCE))
      ? ((Boolean)args.get(REDUCE)).booleanValue() : false;
  }

  public boolean willIncludeDocs() {
    return includedocs;
  }

  public Query setDescending(boolean descending) {
    args.put(DESCENDING, Boolean.valueOf(descending));
    return this;
  }

  public Query setEndkeyDocID(String endkeydocid) {
    args.put(ENDKEYDOCID, endkeydocid);
    return this;
  }

  public Query setGroup(boolean group) {
    args.put(GROUP, Boolean.valueOf(group));
    return this;
  }

  public Query setGroup(boolean group, int grouplevel) {
    args.put(GROUP, new Boolean(group));
    args.put(GROUPLEVEL, Integer.valueOf((grouplevel)));
    return this;
  }

  public Query setIncludeDocs(boolean include) {
    this.includedocs = include;
    return this;
  }

  public Query setInclusiveEnd(boolean inclusiveend) {
    args.put(INCLUSIVEEND, Boolean.valueOf(inclusiveend));
    return this;
  }

  public Query setKey(String key) {
    args.put(KEY, key);
    return this;
  }

  public Query setLimit(int limit) {
    args.put(LIMIT, Integer.valueOf(limit));
    return this;
  }

  public Query setRange(String startkey, String endkey) {
    args.put(ENDKEY, endkey);
    args.put(STARTKEY, startkey);
    return this;
  }

  public Query setRangeStart(String startkey) {
    args.put(STARTKEY, startkey);
    return this;
  }

  public Query setReduce(boolean reduce) {
    args.put(REDUCE, new Boolean(reduce));
    return this;
  }

  public Query setRangeEnd(String endkey) {
    args.put(ENDKEY, endkey);
    return this;
  }

  public Query setSkip(int docstoskip) {
    args.put(SKIP, Integer.valueOf(docstoskip));
    return this;
  }

  public Query setStale(Stale stale) {
    if (stale == Stale.OK) {
      args.put(STALE, stale);
    } else if (stale == Stale.UPDATE_AFTER) {
      args.put(STALE, stale);
    }
    return this;
  }

  public Query setStartkeyDocID(String startkeydocid) {
    args.put(STARTKEYDOCID, startkeydocid);
    return this;
  }

  public Query setUpdateSeq(boolean updateseq) {
    args.put(UPDATESEQ, Boolean.toString(updateseq));
    return this;
  }

  public Query copy() {
    Query query = new Query();

    if (args.containsKey(DESCENDING)) {
      query.setDescending(((Boolean)args.get(DESCENDING)).booleanValue());
    }
    if (args.containsKey(ENDKEY)) {
      query.setRangeEnd(((String)args.get(ENDKEY)));
    }
    if (args.containsKey(ENDKEYDOCID)) {
      query.setEndkeyDocID(((String)args.get(ENDKEYDOCID)));
    }
    if (args.containsKey(GROUP)) {
      query.setGroup(((Boolean)args.get(GROUP)).booleanValue());
    }
    if (args.containsKey(GROUPLEVEL)) {
      query.setGroup(((Boolean)args.get(GROUP)).booleanValue(),
          ((Integer)args.get(GROUPLEVEL)).intValue());
    }
    if (args.containsKey(INCLUSIVEEND)) {
      query.setInclusiveEnd(((Boolean)args.get(INCLUSIVEEND)).booleanValue());
    }
    if (args.containsKey(KEY)) {
      query.setEndkeyDocID(((String)args.get(KEY)));
    }
    if (args.containsKey(LIMIT)) {
      query.setLimit(((Integer)args.get(LIMIT)).intValue());
    }
    if (args.containsKey(REDUCE)) {
      query.setReduce(((Boolean)args.get(REDUCE)).booleanValue());
    }
    if (args.containsKey(SKIP)) {
      query.setSkip(((Integer)args.get(SKIP)).intValue());
    }
    if (args.containsKey(STALE)) {
      query.setStale(((Stale)args.get(STALE)));
    }
    if (args.containsKey(STARTKEY)) {
      query.setRangeStart(((String)args.get(STARTKEY)));
    }
    if (args.containsKey(STARTKEYDOCID)) {
      query.setStartkeyDocID(((String)args.get(STARTKEYDOCID)));
    }
    if (args.containsKey(UPDATESEQ)) {
      query.setUpdateSeq(((Boolean)args.get(UPDATESEQ)).booleanValue());
    }
    setIncludeDocs(willIncludeDocs());

    return query;
  }

  @Override
  public String toString() {
    boolean first = true;
    StringBuffer result = new StringBuffer();
    for (Entry<String, Object> arg : args.entrySet()) {
      if (first) {
        result.append("?");
        first = false;
      } else {
        result.append("&");
      }
      result.append(getArg(arg.getKey(), arg.getValue()));
    }
    return result.toString();
  }

  private String getArg(String key, Object value) {
    if (key.equals(STARTKEYDOCID)) {
      return key + "=" + value;
    } else if (value instanceof Stale) {
      return key + "=" + ((Stale) value).toString();
    } else if (StringUtils.isJsonObject(value.toString())) {
      return key + "=" + value.toString();
    } else {
      return key + "=\"" + value + "\"";
    }
  }
}
