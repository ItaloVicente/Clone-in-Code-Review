      if (mconn instanceof CouchbaseConnection) {
        CouchbaseConnection cbConn = (CouchbaseConnection) mconn;
        cbConn.reconfigure(bucket);
      } else {
        CouchbaseMemcachedConnection cbMConn= (CouchbaseMemcachedConnection) mconn;
        cbMConn.reconfigure(bucket);
      }
