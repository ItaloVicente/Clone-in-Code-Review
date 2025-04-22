
package org.eclipse.jgit.http.server;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.eclipse.jgit.http.server.glue.MetaServlet;
import org.eclipse.jgit.http.server.glue.RegexGroupFilter;
import org.eclipse.jgit.http.server.glue.ServletBinder;
import org.eclipse.jgit.http.server.resolver.FileResolver;
import org.eclipse.jgit.http.server.resolver.GetAnyFile;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.lib.Constants;

public class GitServlet extends MetaServlet {
	private static final long serialVersionUID = 1L;

	private final GetAnyFile getAnyFile;

	private RepositoryResolver resolver;

	public GitServlet() {
		this(null
	}

	public GitServlet(final RepositoryResolver resolver
		if (getAnyFile == null)
			getAnyFile = GetAnyFile.DISABLED;
		this.resolver = resolver;
		this.getAnyFile = getAnyFile;
	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		if (resolver == null) {
			final String basePath = config.getInitParameter("base-path");
			if (basePath == null || "".equals(basePath))
				throw new ServletException("Filter parameter base-path not set");
			resolver = new FileResolver(new File(basePath));
		}

		if (getAnyFile != GetAnyFile.DISABLED) {
			final IsLocalFilter mustBeLocal = new IsLocalFilter();
			final GetAnyFileFilter enabled = new GetAnyFileFilter(getAnyFile);

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

	@Override
	protected ServletBinder register(final ServletBinder b) {
		if (resolver == null)
			throw new IllegalStateException("No resolver available");
		return b.through(new RepositoryFilter(resolver));
	}
}
