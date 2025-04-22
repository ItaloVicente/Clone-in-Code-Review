  /**
   * A strategy that selects and invokes the appropriate setHeader method on
   * the netty HttpHeader class, either setHeader(String, Object) or
   * setHeader(String, String). This indirection is needed as with netty 3.2.0
   * setHeader(String, String) was changed to setHeader(String, Object) and
   * spymemcached users shall be saved from incompatibilities due to an
   * upgrade to the newer netty version. Once netty is upgraded to 3.2.0+ this
   * may strategy can be replaced with a direct invocation of setHeader.
   */
  private static final class HttpMessageHeaders {

    private final Method m;

    private HttpMessageHeaders() {
      this(getHttpMessageHeaderStrategy());
    }

    private HttpMessageHeaders(final Method m) {
      this.m = m;
    }

    private static Method getHttpMessageHeaderStrategy() {
      try {
        return HttpRequest.class.getMethod("setHeader", String.class,
          Object.class);
      } catch (final SecurityException e) {
        throw new RuntimeException(
          "Cannot check method due to security restrictions.", e);
      } catch (final NoSuchMethodException e) {
        try {
          return HttpRequest.class.getMethod("setHeader", String.class,
            String.class);
        } catch (final Exception e1) {
          throw new RuntimeException(
            "No suitable setHeader method found on netty HttpRequest, the "
            + "signature seems to have changed.", e1);
        }
      }
    }

    void setHeader(HttpRequest obj, String name, String value) {
      try {
        m.invoke(obj, name, value);
      } catch (final Exception e) {
        throw new RuntimeException("Could not invoke method " + m
          + " with args '" + name + "' and '" + value + "'.", e);
      }
    }

  }

