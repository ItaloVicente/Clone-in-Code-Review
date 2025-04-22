			ref.init(new FilterConfig() {
				public String getInitParameter(String name) {
					return null;
				}

				public Enumeration getInitParameterNames() {
					return new Enumeration<String>() {
						public boolean hasMoreElements() {
							return false;
						}

						public String nextElement() {
							throw new NoSuchElementException();
						}
					};
				}

				public ServletContext getServletContext() {
					return context;
				}

				public String getFilterName() {
					return ref.getClass().getName();
				}
			});
