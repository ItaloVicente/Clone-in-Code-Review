  public Boolean createDesignDoc(final DesignDocument doc) {
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

  public HttpFuture<Boolean> asyncCreateDesignDoc(String name, String value)
    throws UnsupportedEncodingException {
    getLogger().info("Creating Design Document:" + name);
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();
    final String uri = "/" + bucket + "/_design/" + MODE_PREFIX + name;

    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<Boolean> crv = new HttpFuture<Boolean>(couchLatch, 60000);
    HttpRequest request = new BasicHttpEntityEnclosingRequest("PUT", uri,
            HttpVersion.HTTP_1_1);
    request.setHeader(new BasicHeader("Content-Type", "application/json"));
    StringEntity entity = new StringEntity(value);
    ((BasicHttpEntityEnclosingRequest) request).setEntity(entity);

    HttpOperationImpl op = new DesignDocOperationImpl(request,
      new OperationCallback() {
        @Override
        public void receivedStatus(OperationStatus status) {
          crv.set(status.getMessage().equals("Error Code: 201"), status);
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

  public HttpFuture<Boolean> asyncCreateDesignDoc(final DesignDocument doc)
    throws UnsupportedEncodingException {
    return asyncCreateDesignDoc(doc.getName(), doc.toJson());
  }

  public Boolean deleteDesignDoc(final String name) {
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

  public HttpFuture<Boolean> asyncDeleteDesignDoc(final String name)
    throws UnsupportedEncodingException {
    getLogger().info("Deleting Design Document:" + name);
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();

    final String uri = "/" + bucket + "/_design/" + MODE_PREFIX + name;

    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<Boolean> crv = new HttpFuture<Boolean>(couchLatch, 60000);
    HttpRequest request = new BasicHttpEntityEnclosingRequest("DELETE", uri,
            HttpVersion.HTTP_1_1);
    request.setHeader(new BasicHeader("Content-Type", "application/json"));

    HttpOperationImpl op = new DesignDocOperationImpl(request,
      new OperationCallback() {
        @Override
        public void receivedStatus(OperationStatus status) {
          crv.set(status.getMessage().equals("Error Code: 200"), status);
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

