  static class MyHttpRequestExecutionHandler extends SpyObject
    implements NHttpRequestExecutionHandler  {

    private final ViewConnection vconn;

    public MyHttpRequestExecutionHandler(ViewConnection vconn) {
      super();
      this.vconn = vconn;
    }

    public void initalizeContext(final HttpContext context,
        final Object attachment) {
    }

    public void finalizeContext(final HttpContext context) {
      RequestHandle handle =
          (RequestHandle) context.removeAttribute("request-handle");
      if (handle != null) {
        handle.cancel();
      }
    }

    public HttpRequest submitRequest(final HttpContext context) {
      HttpOperation op = (HttpOperation) context.getAttribute("operation");
      if (op == null) {
        return null;
      }
      return op.getRequest();
    }

    public void handleResponse(final HttpResponse response,
        final HttpContext context) {
      RequestHandle handle =
          (RequestHandle) context.removeAttribute("request-handle");
      HttpOperation op = (HttpOperation) context.removeAttribute("operation");

      try {
        response.setEntity(new BufferedHttpEntity(response.getEntity()));
      } catch(IOException ex) {
        throw new RuntimeException("Could not convert HttpEntity content.");
      }

      int statusCode = response.getStatusLine().getStatusCode();
      if (handle != null) {
        boolean shouldRetry = shouldRetry(statusCode, response);
        if(shouldRetry) {
          if(!op.isTimedOut() && !op.isCancelled()) {
            getLogger().info("Retrying HTTP operation Request: "
              + op.getRequest().getRequestLine() + ", Response: "
              + response.getStatusLine());
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
        handle.completed();
      }
    }

    private boolean shouldRetry(int statusCode, HttpResponse response) {
      switch(statusCode) {
      case 200:
        return false;
      case 404:
        return analyse404Response(response);
      case 500:
        return analyse500Response(response);
      case 300:
      case 301:
      case 302:
      case 303:
      case 307:
      case 401:
      case 408:
      case 409:
      case 412:
      case 416:
      case 417:
      case 501:
      case 502:
      case 503:
      case 504:
        return true;
      default:
        return false;
      }
    }

    private boolean analyse404Response(HttpResponse response) {
      try {
        String body = EntityUtils.toString(response.getEntity());
        if(body.contains("not_found")
          && (body.contains("missing") || body.contains("deleted"))) {
          return false;
        }
      } catch(IOException ex) {
        return false;
      }
      return true;
    }

    private boolean analyse500Response(HttpResponse response) {
      try {
        String body = EntityUtils.toString(response.getEntity());
        if(body.contains("error")
          && body.contains(("{not_found, missing_named_view}"))) {
          return false;
        }
      } catch(IOException ex) {
        return false;
      }
      return true;
    }

    @Override
    public ConsumingNHttpEntity responseEntity(HttpResponse response,
        HttpContext context) throws IOException {
      return new BufferingNHttpEntity(response.getEntity(),
          new HeapByteBufferAllocator());
    }
  }

  static class EventLogger extends SpyObject implements EventListener {

    public void connectionOpen(final NHttpConnection conn) {
      getLogger().debug("Connection open: " + conn);
    }

    public void connectionTimeout(final NHttpConnection conn) {
      getLogger().error("Connection timed out: " + conn);
    }

    public void connectionClosed(final NHttpConnection conn) {
      getLogger().debug("Connection closed: " + conn);
    }

    public void fatalIOException(final IOException ex,
        final NHttpConnection conn) {
      getLogger().error("I/O error: " + ex.getMessage());
    }

    public void fatalProtocolException(final HttpException ex,
        final NHttpConnection conn) {
      getLogger().error("HTTP error: " + ex.getMessage());
    }
  }
