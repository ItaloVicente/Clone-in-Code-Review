
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.PUBLISH_SUBSCRIBE_REQUEST_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.PUBLISH_SUBSCRIBE_RESULT_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.sendError;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_HANDLER;
import static org.eclipse.jgit.http.server.ServletUtils.getInputStream;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.transport.PublisherClient;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.PublisherClientFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class PublisherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static class Factory implements Filter {
		private final PublisherClientFactory<HttpServletRequest>
				publisherFactory;

		Factory(PublisherClientFactory<HttpServletRequest> publisherFactory) {
			this.publisherFactory = publisherFactory;
		}

		public void doFilter(ServletRequest request
				FilterChain chain) throws IOException
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse rsp = (HttpServletResponse) response;
			PublisherClient pc;
			try {
				pc = publisherFactory.create(req);
			} catch (ServiceNotEnabledException e) {
				sendError(req
				return;
			} catch (ServiceNotAuthorizedException e) {
				rsp.sendError(SC_UNAUTHORIZED);
				return;
			}

			try {
				req.setAttribute(ATTRIBUTE_HANDLER
				chain.doFilter(req
			} finally {
				req.removeAttribute(ATTRIBUTE_HANDLER);
			}
		}

		public void init(FilterConfig filterConfig) throws ServletException {
		}

		public void destroy() {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req
			throws ServletException
		if (!PUBLISH_SUBSCRIBE_REQUEST_TYPE.equals(req.getContentType())) {
			rsp.sendError(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}
		SmartOutputStream out = new SmartOutputStream(req
			@Override
			public void flush() throws IOException {
				doFlush();
			}
		};

		PublisherClient pc = (PublisherClient) req.getAttribute(ATTRIBUTE_HANDLER);
		try {
			rsp.setContentType(PUBLISH_SUBSCRIBE_RESULT_TYPE);
			pc.subscribe(getInputStream(req)
			out.close();
		} catch (ServiceMayNotContinueException e) {
			if (e.isOutput())
				out.close();
			else if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
		} catch (ServiceNotAuthorizedException e) {
			if (!rsp.isCommitted()) {
				rsp.reset();
				rsp.sendError(SC_UNAUTHORIZED);
			}
		} catch (Throwable e) {
			getServletContext().log(
					HttpServerText.get().internalErrorDuringPublishSubscribe
					e);
			if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
		}
	}
}
