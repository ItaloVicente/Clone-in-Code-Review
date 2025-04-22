		return register(new RegexPipeline.Binder(expression));
	}

	public void destroy() {
		if (pipelines != null) {
			Set<Object> destroyed = newIdentitySet();
			for (UrlPipeline p : pipelines)
				p.destroy(destroyed);
			pipelines = null;
		}
	}

	private static Set<Object> newIdentitySet() {
		final Map<Object, Object> m = new IdentityHashMap<Object, Object>();
		return new AbstractSet<Object>() {
			@Override
			public boolean add(Object o) {
				return m.put(o, o) == null;
			}

			@Override
			public boolean contains(Object o) {
				return m.keySet().contains(o);
			}

			@Override
			public Iterator<Object> iterator() {
				return m.keySet().iterator();
			}

			@Override
			public int size() {
				return m.size();
			}
		};
