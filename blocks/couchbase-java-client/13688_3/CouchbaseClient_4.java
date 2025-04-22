

  public HttpFuture<View> asyncGetView(String designDocumentName,
      final String viewName) {
    designDocumentName = MODE_PREFIX + designDocumentName;
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();
    String uri = "/" + bucket + "/_design/" + designDocumentName;
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<View> crv = new HttpFuture<View>(couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op =
        new ViewFetcherOperationImpl(request, bucket, designDocumentName,
            viewName, new ViewFetcherOperation.ViewFetcherCallback() {
              private View view = null;

              @Override
              public void receivedStatus(OperationStatus status) {
                crv.set(view, status);
              }

              @Override
              public void complete() {
                couchLatch.countDown();
              }

              @Override
              public void gotData(View v) {
                view = v;
              }
            });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public HttpFuture<List<View>> asyncGetViews(String designDocumentName) {
    designDocumentName = MODE_PREFIX + designDocumentName;
    String bucket = ((CouchbaseConnectionFactory)connFactory).getBucketName();
    String uri = "/" + bucket + "/_design/" + designDocumentName;
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<List<View>> crv =
        new HttpFuture<List<View>>(couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op = new ViewsFetcherOperationImpl(request, bucket,
        designDocumentName, new ViewsFetcherOperation.ViewsFetcherCallback() {
          private List<View> views = null;

          @Override
          public void receivedStatus(OperationStatus status) {
            crv.set(views, status);
          }

          @Override
          public void complete() {
            couchLatch.countDown();
          }

          @Override
          public void gotData(List<View> v) {
            views = v;
          }
        });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public View getView(final String designDocumentName, final String viewName) {
    try {
      return asyncGetView(designDocumentName, viewName).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted getting views", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed getting views", e);
    }
  }

  public List<View> getViews(final String designDocumentName) {
    try {
      return asyncGetViews(designDocumentName).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted getting views", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed getting views", e);
    }
  }

  public HttpFuture<ViewResponse> asyncQuery(View view, Query query) {
    if (query.willReduce()) {
      return asyncQueryAndReduce(view, query);
    } else if (query.willIncludeDocs()) {
      return asyncQueryAndIncludeDocs(view, query);
    } else {
      return asyncQueryAndExcludeDocs(view, query);
    }
  }

  private HttpFuture<ViewResponse> asyncQueryAndIncludeDocs(View view,
      Query query) {
    String uri = view.getURI() + query.toString();
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final ViewFuture crv = new ViewFuture(couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op = new DocsOperationImpl(request, new ViewCallback() {
      private ViewResponse vr = null;

      @Override
      public void receivedStatus(OperationStatus status) {
        if (vr != null) {
          Collection<String> ids = new LinkedList<String>();
          Iterator<ViewRow> itr = vr.iterator();
          while (itr.hasNext()) {
            ids.add(itr.next().getId());
          }
          crv.set(vr, asyncGetBulk(ids), status);
        } else {
          crv.set(null, null, status);
        }
      }

      @Override
      public void complete() {
        couchLatch.countDown();
      }

      @Override
      public void gotData(ViewResponse response) {
        vr = response;
      }
    });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  private HttpFuture<ViewResponse> asyncQueryAndExcludeDocs(View view,
      Query query) {
    String uri = view.getURI() + query.toString();
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<ViewResponse> crv =
        new HttpFuture<ViewResponse>(couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op =
        new NoDocsOperationImpl(request, new ViewCallback() {
          private ViewResponse vr = null;

          @Override
          public void receivedStatus(OperationStatus status) {
            crv.set(vr, status);
          }

          @Override
          public void complete() {
            couchLatch.countDown();
          }

          @Override
          public void gotData(ViewResponse response) {
            vr = response;
          }
        });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  private HttpFuture<ViewResponse> asyncQueryAndReduce(final View view,
      final Query query) {
    if (!view.hasReduce()) {
      throw new RuntimeException("This view doesn't contain a reduce function");
    }
    String uri = view.getURI() + query.toString();
    final CountDownLatch couchLatch = new CountDownLatch(1);
    final HttpFuture<ViewResponse> crv =
        new HttpFuture<ViewResponse>(couchLatch, 60000);

    final HttpRequest request =
        new BasicHttpRequest("GET", uri, HttpVersion.HTTP_1_1);
    final HttpOperation op =
        new ReducedOperationImpl(request, new ViewCallback() {
          private ViewResponse vr = null;

          @Override
          public void receivedStatus(OperationStatus status) {
            crv.set(vr, status);
          }

          @Override
          public void complete() {
            couchLatch.countDown();
          }

          @Override
          public void gotData(ViewResponse response) {
            vr = response;
          }
        });
    crv.setOperation(op);
    addOp(op);
    return crv;
  }

  public ViewResponse query(View view, Query query) {
    try {
      return asyncQuery(view, query).get();
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted while accessing the view", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Failed to access the view", e);
    }
  }

  public Paginator paginatedQuery(View view, Query query, int docsPerPage) {
    return new Paginator(this, view, query, 10);
  }

  public void addOp(final HttpOperation op) {
    vconn.checkState();
    vconn.addOp(op);
  }


