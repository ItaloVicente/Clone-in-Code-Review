	public Map<String
		Map<String
		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED
		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTPS_ENABLED
		return gitPrefs;
	}

	@Test
	public void testRoot() {
		base("/");
		assertThat(provider.getFullHostNames().get("http")).isNull();
		assertThat(provider.getFullHostNames().get("https")).isNotNull();
	}

	public void base(final String contextPath) {
		final HTTPSupport httpSupport = new HTTPSupport() {
			@Override
			protected JGitFileSystemProvider resolveProvider() {
				return provider;
			}
		};

		final ServletContextEvent sce = mock(ServletContextEvent.class);

		final ServletContext sc = mock(ServletContext.class);
		final ServletRegistration.Dynamic dyn = mock(ServletRegistration.Dynamic.class);

		ArgumentCaptor<Servlet> servletArgumentCaptor = ArgumentCaptor.forClass(Servlet.class);

		when(sc.addServlet(anyString()

		when(sce.getServletContext()).thenReturn(sc);
		when(sc.getContextPath()).thenReturn(contextPath);

		httpSupport.contextInitialized(sce);

		verify(sc
		verify(dyn
		verify(dyn
		verify(dyn
	}
