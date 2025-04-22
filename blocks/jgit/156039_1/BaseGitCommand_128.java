
package org.eclipse.jgit.niofs.internal.daemon.http;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class HTTPSupport implements ServletContextListener {

    private static final String GIT_PATH = "git";
    private static final Logger LOG = LoggerFactory.getLogger(HTTPSupport.class);

    private ServletContext servletContext = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        final JGitFileSystemProvider fsProvider = resolveProvider();
        if (fsProvider != null && (fsProvider.getConfig().isHttpEnabled() || fsProvider.getConfig().isHttpsEnabled())) {
            if (fsProvider.getConfig().isHttpEnabled()) {
                fsProvider.addHostName("http"
            }
            if (fsProvider.getConfig().isHttpsEnabled()) {
                fsProvider.addHostName("https"
                                       fsProvider.getConfig().getHttpsHostName() + ":" + fsProvider.getConfig().getHttpsPort() + servletContext.getContextPath() + "/" + GIT_PATH);
            }
            final GitServlet gitServlet = new GitServlet();
            gitServlet.setRepositoryResolver(fsProvider.getRepositoryResolver());
            gitServlet.setAsIsFileService(null);
            gitServlet.setReceivePackFactory((req
            ServletRegistration.Dynamic sd = servletContext.addServlet("GitServlet"
            sd.setLoadOnStartup(1);
            sd.setAsyncSupported(false);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }

    protected JGitFileSystemProvider resolveProvider() {
        try {
            return null;
        } catch (Exception ex) {
            LOG.error("Error trying to resolve JGitFileSystemProvider."
        }
        return null;
    }
}
