
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.RECEIVE_PACK;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.RECEIVE_PACK_REQUEST_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.RECEIVE_PACK_RESULT_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.sendError;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_HANDLER;
import static org.eclipse.jgit.http.server.ServletUtils.consumeRequestBody;
import static org.eclipse.jgit.http.server.ServletUtils.getInputStream;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.util.HttpSupport.HDR_USER_AGENT;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.InternalHttpServerGlue;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

class ReceivePackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static class InfoRefs extends SmartServiceInfoRefs {
		private final ReceivePackFactory<HttpServletRequest> receivePackFactory;

		InfoRefs(ReceivePackFactory<HttpServletRequest> receivePackFactory
				List<Filter> filters) {
			super(RECEIVE_PACK
			this.receivePackFactory = receivePackFactory;
		}

		@Override
		protected void begin(HttpServletRequest req
				throws IOException
				ServiceNotAuthorizedException {
			ReceivePack rp = receivePackFactory.create(req
			InternalHttpServerGlue.setPeerUserAgent(
					rp
					req.getHeader(HDR_USER_AGENT));
			req.setAttribute(ATTRIBUTE_HANDLER
		}

		@Override
		protected void advertise(HttpServletRequest req
				PacketLineOutRefAdvertiser pck) throws IOException
				ServiceNotEnabledException
			ReceivePack rp = (ReceivePack) req.getAttribute(ATTRIBUTE_HANDLER);
			try {
				rp.sendAdvertisedRefs(pck);
			} finally {
				rp.getRevWalk().close();
			}
		}
	}

	static class Factory implements Filter {
		private final ReceivePackFactory<HttpServletRequest> receivePackFactory;

		Factory(ReceivePackFactory<HttpServletRequest> receivePackFactory) {
			this.receivePackFactory = receivePackFactory;
		}

		@Override
		public void doFilter(ServletRequest request
				FilterChain chain) throws IOException
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse rsp = (HttpServletResponse) response;
			ReceivePack rp;
			try {
				rp = receivePackFactory.create(req
			} catch (ServiceNotAuthorizedException e) {
				rsp.sendError(SC_UNAUTHORIZED
				return;
			} catch (ServiceNotEnabledException e) {
				sendError(req
				return;
			}

			try {
				req.setAttribute(ATTRIBUTE_HANDLER
				chain.doFilter(req
			} finally {
				req.removeAttribute(ATTRIBUTE_HANDLER);
			}
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
		}

		@Override
		public void destroy() {
		}
	}

	@Override
	public void doPost(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		if (!RECEIVE_PACK_REQUEST_TYPE.equals(req.getContentType())) {
			rsp.sendError(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		SmartOutputStream out = new SmartOutputStream(req
			@Override
			public void flush() throws IOException {
				doFlush();
			}
		};

		ReceivePack rp = (ReceivePack) req.getAttribute(ATTRIBUTE_HANDLER);
		try {
			rp.setBiDirectionalPipe(false);
			rsp.setContentType(RECEIVE_PACK_RESULT_TYPE);

			rp.receive(getInputStream(req)
			out.close();
		} catch (CorruptObjectException e ) {
			getServletContext().log(MessageFormat.format(
					HttpServerText.get().receivedCorruptObject
					e.getMessage()
					ServletUtils.identify(rp.getRepository())));
			consumeRequestBody(req);
			out.close();

		} catch (UnpackException | PackProtocolException e) {
			log(rp.getRepository()
			consumeRequestBody(req);
			out.close();

		} catch (Throwable e) {
			log(rp.getRepository()
			if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
			return;
		}
	}

	private void log(Repository git
		getServletContext().log(MessageFormat.format(
				HttpServerText.get().internalErrorDuringReceivePack
				ServletUtils.identify(git))
	}
}
