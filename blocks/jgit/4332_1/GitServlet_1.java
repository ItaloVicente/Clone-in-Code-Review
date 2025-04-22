		gitFilter.init(new FilterConfig() {
			public String getFilterName() {
				return gitFilter.getClass().getName();
			}

			public String getInitParameter(String name) {
				return config.getInitParameter(name);
			}

			public Enumeration getInitParameterNames() {
				return config.getInitParameterNames();
			}

			public ServletContext getServletContext() {
				return config.getServletContext();
			}
		});
