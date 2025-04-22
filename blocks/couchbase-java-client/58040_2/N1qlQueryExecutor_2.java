    private GenericQueryRequest createN1qlRequest(final N1qlQuery query, String bucket, String password) {
        String rawQuery = query.n1ql().toString();
        rawQuery = rawQuery.replace(
          CouchbaseAsyncBucket.CURRENT_BUCKET_IDENTIFIER,
          "`" + bucket + "`"
        );
        return GenericQueryRequest.jsonQuery(rawQuery, bucket, password);
    }

