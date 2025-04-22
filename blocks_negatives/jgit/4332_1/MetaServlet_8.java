		return b;
	}

	private UrlPipeline[] getPipelines() throws ServletException {
		UrlPipeline[] r = pipelines;
		if (r == null) {
			synchronized (bindings) {
				r = pipelines;
				if (r == null) {
					r = createPipelines();
					pipelines = r;
				}
			}
		}
		return r;
	}

	private UrlPipeline[] createPipelines() throws ServletException {
		UrlPipeline[] array = new UrlPipeline[bindings.size()];

		for (int i = 0; i < bindings.size(); i++)
			array[i] = bindings.get(i).create();

		Set<Object> inited = newIdentitySet();
		for (UrlPipeline p : array)
			p.init(getServletContext(), inited);
		return array;
