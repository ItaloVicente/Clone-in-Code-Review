
package org.eclipse.jgit.http.server;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.glue.ErrorServlet;
import org.eclipse.jgit.http.server.glue.MetaFilter;
import org.eclipse.jgit.http.server.glue.RegexGroupFilter;
import org.eclipse.jgit.http.server.glue.ServletBinder;
import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.http.server.resolver.DefaultUploadPackFactory;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.eclipse.jgit.util.StringUtils;

public class GitFilter extends MetaFilter {
	private volatile boolean initialized;

	private RepositoryResolver<HttpServletRequest> resolver;

	private AsIsFileService asIs = new AsIsFileService();

	private UploadPackFactory<HttpServletRequest> uploadPackFactory = new DefaultUploadPackFactory();

	private ReceivePackFactory<HttpServletRequest> receivePackFactory = new DefaultReceivePackFactory();

	private final List<Filter> uploadPackFilters = new LinkedList<>();

	private final List<Filter> receivePackFilters = new LinkedList<>();

	public GitFilter() {
	}

	public void setRepositoryResolver(RepositoryResolver<HttpServletRequest> resolver) {
		assertNotInitialized();
		this.resolver = resolver;
	}

	public void setAsIsFileService(AsIsFileService f) {
		assertNotInitialized();
		this.asIs = f != null ? f : AsIsFileService.DISABLED;
	}

	@SuppressWarnings("unchecked")
	public void setUploadPackFactory(UploadPackFactory<HttpServletRequest> f) {
		assertNotInitialized();
		this.uploadPackFactory = f != null ? f : (UploadPackFactory<HttpServletRequest>)UploadPackFactory.DISABLED;
	}

	public void addUploadPackFilter(Filter filter) {
		assertNotInitialized();
		uploadPackFilters.add(filter);
	}

	@SuppressWarnings("unchecked")
	public void setReceivePackFactory(ReceivePackFactory<HttpServletRequest> f) {
		assertNotInitialized();
		this.receivePackFactory = f != null ? f : (ReceivePackFactory<HttpServletRequest>)ReceivePackFactory.DISABLED;
	}

	public void addReceivePackFilter(Filter filter) {
		assertNotInitialized();
		receivePackFilters.add(filter);
	}

	private void assertNotInitialized() {
		if (initialized)
			throw new IllegalStateException(HttpServerText.get().alreadyInitializedByContainer);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);

		if (resolver == null) {
			File root = getFile(filterConfig
			boolean exportAll = getBoolean(filterConfig
			setRepositoryResolver(new FileResolver<HttpServletRequest>(root
		}

		initialized = true;

		if (uploadPackFactory != UploadPackFactory.DISABLED) {
			b = b.through(new UploadPackServlet.Factory(uploadPackFactory));
			for (Filter f : uploadPackFilters)
				b = b.through(f);
			b.with(new UploadPackServlet());
		}

		if (receivePackFactory != ReceivePackFactory.DISABLED) {
			b = b.through(new ReceivePackServlet.Factory(receivePackFactory));
			for (Filter f : receivePackFilters)
				b = b.through(f);
			b.with(new ReceivePackServlet());
		}

		if (uploadPackFactory != UploadPackFactory.DISABLED) {
			refs = refs.through(new UploadPackServlet.InfoRefs(
					uploadPackFactory
		}
		if (receivePackFactory != ReceivePackFactory.DISABLED) {
			refs = refs.through(new ReceivePackServlet.InfoRefs(
					receivePackFactory
		}
		if (asIs != AsIsFileService.DISABLED) {
			refs = refs.through(new IsLocalFilter());
			refs = refs.through(new AsIsFileFilter(asIs));
			refs.with(new InfoRefsServlet());
		} else
			refs.with(new ErrorServlet(HttpServletResponse.SC_NOT_ACCEPTABLE));

		if (asIs != AsIsFileService.DISABLED) {
			final IsLocalFilter mustBeLocal = new IsLocalFilter();
			final AsIsFileFilter enabled = new AsIsFileFilter(asIs);

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

	private static File getFile(FilterConfig cfg
			throws ServletException {
		String n = cfg.getInitParameter(param);
		if (n == null || "".equals(n))
			throw new ServletException(MessageFormat.format(HttpServerText.get().parameterNotSet

		File path = new File(n);
		if (!path.exists())
			throw new ServletException(MessageFormat.format(HttpServerText.get().pathForParamNotFound
		return path;
	}

	private static boolean getBoolean(FilterConfig cfg
			throws ServletException {
		String n = cfg.getInitParameter(param);
		if (n == null)
			return false;
		try {
			return StringUtils.toBoolean(n);
		} catch (IllegalArgumentException err) {
			throw new ServletException(MessageFormat.format(HttpServerText.get().invalidBoolean
		}
	}

	@Override
	protected ServletBinder register(ServletBinder binder) {
		if (resolver == null)
			throw new IllegalStateException(HttpServerText.get().noResolverAvailable);
		binder = binder.through(new NoCacheFilter());
		binder = binder.through(new RepositoryFilter(resolver));
		return binder;
	}
}
