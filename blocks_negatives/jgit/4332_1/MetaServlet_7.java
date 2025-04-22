	private ServletBinder register(ServletBinderImpl b) {
		synchronized (bindings) {
			if (pipelines != null)
				throw new IllegalStateException(HttpServerText.get().servletAlreadyInitialized);
			bindings.add(b);
		}
		return register((ServletBinder) b);
