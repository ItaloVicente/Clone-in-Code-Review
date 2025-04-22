		this(new MetaFilter());
	}

	protected MetaServlet(MetaFilter delegateFilter) {
		filter = delegateFilter;
	}

	protected MetaFilter getDelegateFilter() {
		return filter;
