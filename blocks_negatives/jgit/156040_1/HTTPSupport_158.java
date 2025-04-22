    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        final JGitFileSystemProvider fsProvider = resolveProvider();
        if (fsProvider != null && (fsProvider.getConfig().isHttpEnabled() || fsProvider.getConfig().isHttpsEnabled())) {
            if (fsProvider.getConfig().isHttpEnabled()) {
                fsProvider.addHostName("http", fsProvider.getConfig().getHttpHostName() + ":" + fsProvider.getConfig().getHttpPort() + servletContext.getContextPath() + "/" + GIT_PATH);
            }
            if (fsProvider.getConfig().isHttpsEnabled()) {
                fsProvider.addHostName("https",
                                       fsProvider.getConfig().getHttpsHostName() + ":" + fsProvider.getConfig().getHttpsPort() + servletContext.getContextPath() + "/" + GIT_PATH);
            }
            final GitServlet gitServlet = new GitServlet();
            gitServlet.setRepositoryResolver(fsProvider.getRepositoryResolver());
            gitServlet.setAsIsFileService(null);
            gitServlet.setReceivePackFactory((req, db) -> fsProvider.getReceivePack("http", req, db));
            ServletRegistration.Dynamic sd = servletContext.addServlet("GitServlet", gitServlet);
            sd.addMapping("/" + GIT_PATH + "/*");
            sd.setLoadOnStartup(1);
            sd.setAsyncSupported(false);
        }
    }
