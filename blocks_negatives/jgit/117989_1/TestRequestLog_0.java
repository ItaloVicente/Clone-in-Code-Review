  private static final int MAX = 16;

  private final List<AccessEvent> events = new ArrayList<>();

  private final Semaphore active = new Semaphore(MAX);

  /** Reset the log back to its original empty state. */
  void clear() {
    try {
      for (;;) {
        try {
          active.acquire(MAX);
          break;
        } catch (InterruptedException e) {
          continue;
        }
      }

      synchronized (events) {
        events.clear();
      }
    } finally {
      active.release(MAX);
    }
  }

  /** @return all of the events made since the last clear. */
  List<AccessEvent> getEvents() {
    try {
      for (;;) {
        try {
          active.acquire(MAX);
          break;
        } catch (InterruptedException e) {
          continue;
        }
      }

      synchronized (events) {
        return events;
      }
    } finally {
      active.release(MAX);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    try {
      for (;;) {
        try {
          active.acquire();
          break;
        } catch (InterruptedException e) {
          continue;
        }
      }

      super.handle(target, baseRequest, request, response);

      if (DispatcherType.REQUEST.equals(baseRequest.getDispatcherType()))
        log((Request) request, (Response) response);

    } finally {
      active.release();
    }
  }

  private void log(Request request, Response response) {
    synchronized (events) {
      events.add(new AccessEvent(request, response));
    }
  }
