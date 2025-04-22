  public ReducedOperationImpl(HttpRequest r, AbstractView view,
    ViewCallback cb) {
    super(r, view, cb);

    if(getView() instanceof SpatialView) {
      throw new IllegalArgumentException("Spatial views do not support reduce");
    }
