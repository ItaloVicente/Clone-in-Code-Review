
package org.eclipse.jgit.http.server.glue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.http.server.HttpServerText;

public class MetaFilter implements Filter {
	static final String REGEX_GROUPS = "org.eclipse.jgit.http.server.glue.MetaServlet.serveRegex";

	private ServletContext servletContext;

	private final List<ServletBinderImpl> bindings;

	private volatile UrlPipeline[] pipelines;

	public MetaFilter() {
		this.bindings = new ArrayList<ServletBinderImpl>();
	}

	public ServletBinder serve(String path) {
		if (path.startsWith("*"))
			return register(new SuffixPipeline.Binder(path.substring(1)));
		throw new IllegalArgumentException(MessageFormat.format(HttpServerText
				.get().pathNotSupported
	}

	public ServletBinder serveRegex(String expression) {
		return register(new RegexPipeline.Binder(expression));
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}

	public void destroy() {
		if (pipelines != null) {
			Set<Object> destroyed = newIdentitySet();
			for (UrlPipeline p : pipelines)
				p.destroy(destroyed);
			pipelines = null;
		}
	}

	private static Set<Object> newIdentitySet() {
		final Map<Object
		return new AbstractSet<Object>() {
			@Override
			public boolean add(Object o) {
				return m.put(o
			}

			@Override
			public boolean contains(Object o) {
				return m.keySet().contains(o);
			}

			@Override
			public Iterator<Object> iterator() {
				return m.keySet().iterator();
			}

			@Override
			public int size() {
				return m.size();
			}
		};
	}

	public void doFilter(ServletRequest request
			FilterChain chain) throws IOException
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		UrlPipeline p = find(req);
		if (p != null)
			p.service(req
		else
			chain.doFilter(req
	}

	private UrlPipeline find(HttpServletRequest req) throws ServletException {
		for (UrlPipeline p : getPipelines())
			if (p.match(req))
				return p;
		return null;
	}

	private ServletBinder register(ServletBinderImpl b) {
		synchronized (bindings) {
			if (pipelines != null)
				throw new IllegalStateException(
						HttpServerText.get().servletAlreadyInitialized);
			bindings.add(b);
		}
		return register((ServletBinder) b);
	}

	protected ServletBinder register(ServletBinder b) {
		return b;
	}

	private UrlPipeline[] getPipelines() throws ServletException {
		UrlPipeline[] r = pipelines;
		if (r == null) {
			synchronized (bindings) {
				r = pipelines;
				if (r == null) {
					r = createPipelines();
					pipelines = r;
				}
			}
		}
		return r;
	}

	private UrlPipeline[] createPipelines() throws ServletException {
		UrlPipeline[] array = new UrlPipeline[bindings.size()];

		for (int i = 0; i < bindings.size(); i++)
			array[i] = bindings.get(i).create();

		Set<Object> inited = newIdentitySet();
		for (UrlPipeline p : array)
			p.init(servletContext
		return array;
	}
}
