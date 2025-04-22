
	private class Chain implements FilterChain {
		private int filterIdx;

		public void doFilter(ServletRequest req
				throws IOException
			if (filterIdx < filters.length)
				filters[filterIdx++].doFilter(req
			else
				service(req
		}
	}
