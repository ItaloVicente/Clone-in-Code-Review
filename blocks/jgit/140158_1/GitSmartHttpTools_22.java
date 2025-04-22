
package org.eclipse.jgit.http.server;

import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.http.server.glue.MetaServlet;
import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

public class GitServlet extends MetaServlet {
	private static final long serialVersionUID = 1L;

	private final GitFilter gitFilter;

	public GitServlet() {
		super(new GitFilter());
		gitFilter = (GitFilter) getDelegateFilter();
	}

	public void setRepositoryResolver(RepositoryResolver<HttpServletRequest> resolver) {
		gitFilter.setRepositoryResolver(resolver);
	}

	public void setAsIsFileService(AsIsFileService f) {
		gitFilter.setAsIsFileService(f);
	}

	public void setUploadPackFactory(UploadPackFactory<HttpServletRequest> f) {
		gitFilter.setUploadPackFactory(f);
	}

	public void addUploadPackFilter(Filter filter) {
		gitFilter.addUploadPackFilter(filter);
	}

	public void setReceivePackFactory(ReceivePackFactory<HttpServletRequest> f) {
		gitFilter.setReceivePackFactory(f);
	}

	public void addReceivePackFilter(Filter filter) {
		gitFilter.addReceivePackFilter(filter);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		gitFilter.init(new FilterConfig() {
			@Override
			public String getFilterName() {
				return gitFilter.getClass().getName();
			}

			@Override
			public String getInitParameter(String name) {
				return config.getInitParameter(name);
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				return config.getInitParameterNames();
			}

			@Override
			public ServletContext getServletContext() {
				return config.getServletContext();
			}
		});
	}
}
