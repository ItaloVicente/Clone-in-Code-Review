    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED, "true");
        return gitPrefs;
    }

    @Test
    public void testRoot() {
        base("");
        assertThat(provider.getFullHostNames().get("http")).isEqualTo("localhost:8080/git");
    }

    @Test
    public void testContext() {
        base("/app-former");
        assertThat(provider.getFullHostNames().get("http")).isEqualTo("localhost:8080/app-former/git");
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

        when(sc.addServlet(anyString(), servletArgumentCaptor.capture())).thenReturn(dyn);

        when(sce.getServletContext()).thenReturn(sc);
        when(sc.getContextPath()).thenReturn(contextPath);

        httpSupport.contextInitialized(sce);

        verify(sc, times(1)).addServlet(anyString(), any(Servlet.class));
        verify(dyn, times(1)).addMapping("/git/*");
        verify(dyn, times(1)).setLoadOnStartup(1);
        verify(dyn, times(1)).setAsyncSupported(false);
    }
