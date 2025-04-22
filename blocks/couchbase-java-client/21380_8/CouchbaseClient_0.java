

  public String createDesignDoc(final DesignDocument doc) {
     try {
      return asyncCreateDesignDoc(doc).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted creating design document", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed creating design document", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Failed creating design document", e);
    }
  }

  public HttpFuture<String> asyncCreateDesignDoc(final DesignDocument doc)
    throws UnsupportedEncodingException {
    getLogger().info("Creating Design Document:" + doc.getName());
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();
    final String uri = "/" + bucket + "/_design/" + MODE_PREFIX + doc.getName();

    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<String> crv = new HttpFuture<String>(couchLatch, 60000);
    HttpRequest request = new BasicHttpEntityEnclosingRequest("PUT", uri,
            HttpVersion.HTTP_1_1);
    request.setHeader(new BasicHeader("Content-Type", "application/json"));
    StringEntity entity = new StringEntity(doc.toJson());
    ((BasicHttpEntityEnclosingRequest) request).setEntity(entity);

    HttpOperationImpl op = new DesignDocOperationImpl(request,
      new OperationCallback() {
        String result = null;

        @Override
        public void receivedStatus(OperationStatus status) {
          crv.set(result, status);
        }

        @Override
        public void complete() {
          couchLatch.countDown();
        }
    });

    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public String deleteDesignDoc(final String name) {
     try {
      return asyncDeleteDesignDoc(name).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted deleting design document", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed deleting design document", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Failed deleting design document", e);
    }
  }

  public HttpFuture<String> asyncDeleteDesignDoc(final String name)
    throws UnsupportedEncodingException {
    getLogger().info("Deleting Design Document:" + name);
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();

    final String uri = "/" + bucket + "/_design/" + MODE_PREFIX + name;

    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<String> crv = new HttpFuture<String>(couchLatch, 60000);
    HttpRequest request = new BasicHttpEntityEnclosingRequest("DELETE", uri,
            HttpVersion.HTTP_1_1);
    request.setHeader(new BasicHeader("Content-Type", "application/json"));

    HttpOperationImpl op = new DesignDocOperationImpl(request,
      new OperationCallback() {
        String result = null;

        @Override
        public void receivedStatus(OperationStatus status) {
          crv.set(result, status);
        }

        @Override
        public void complete() {
          couchLatch.countDown();
        }
    });

    crv.setOperation(op);
    addOp(op);
    return crv;
  }

