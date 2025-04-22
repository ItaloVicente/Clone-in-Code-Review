
package org.eclipse.jgit.http.server;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.eclipse.jgit.http.server.glue.MetaServlet;
import org.eclipse.jgit.http.server.glue.RegexGroupFilter;
import org.eclipse.jgit.http.server.glue.ServletBinder;
import org.eclipse.jgit.http.server.resolver.FileResolver;
import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.StringUtils;

public class GitServlet extends MetaServlet {
	private static final long serialVersionUID = 1L;

	private volatile boolean initialized;

	private RepositoryResolver resolver;

	private AsIsFileService asIs = new AsIsFileService();

	public GitServlet() {
	}

	public void setRepositoryResolver(RepositoryResolver resolver) {
		assertNotInitialized();
		this.resolver = resolver;
	}

	public void setAsIsFileService(AsIsFileService f) {
		assertNotInitialized();
		this.asIs = f != null ? f : AsIsFileService.DISABLED;
	}

	private void assertNotInitialized() {
		if (initialized)
			throw new IllegalStateException("Already initialized by container");
	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		if (resolver == null) {
			final File root = getFile("base-path");
			final boolean exportAll = getBoolean("export-all");
			setRepositoryResolver(new FileResolver(root
		}

		initialized = true;

		if (asIs != AsIsFileService.DISABLED) {
			final IsLocalFilter mustBeLocal = new IsLocalFilter();
			final AsIsFileFilter enabled = new AsIsFileFilter(asIs);

					.with(new InfoRefsServlet());

					.with(new TextFileServlet(Constants.HEAD));

			final String info_alternates = "objects/info/alternates";
					.with(new TextFileServlet(info_alternates));

			final String http_alternates = "objects/info/http-alternates";
					.with(new TextFileServlet(http_alternates));

					.with(new InfoPacksServlet());

					.with(new ObjectFileServlet.Loose());

					.with(new ObjectFileServlet.Pack());

					.with(new ObjectFileServlet.PackIdx());
		}
	}

	private File getFile(final String param) throws ServletException {
		String n = getInitParameter(param);
		if (n == null || "".equals(n))
			throw new ServletException("Parameter " + param + " not set");

		File path = new File(n);
		if (!path.exists())
			throw new ServletException(path + " (for " + param + ") not found");
		return path;
	}

	private boolean getBoolean(String param) throws ServletException {
		String n = getInitParameter(param);
		if (n == null)
			return false;
		try {
			return StringUtils.toBoolean(n);
		} catch (IllegalArgumentException err) {
			throw new ServletException("Invalid boolean " + param + " = " + n);
		}
	}

	@Override
	protected ServletBinder register(ServletBinder binder) {
		if (resolver == null)
			throw new IllegalStateException("No resolver available");
		binder = binder.through(new NoCacheFilter());
		binder = binder.through(new RepositoryFilter(resolver));
		return binder;
	}
}
