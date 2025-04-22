
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.UPLOAD_PACK;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.UPLOAD_PACK_REQUEST_TYPE;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.UPLOAD_PACK_RESULT_TYPE;
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

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.InternalHttpServerGlue;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.UploadPackInternalServerErrorException;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

class UploadPackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static class InfoRefs extends SmartServiceInfoRefs {
		private final UploadPackFactory<HttpServletRequest> uploadPackFactory;

		InfoRefs(UploadPackFactory<HttpServletRequest> uploadPackFactory
				List<Filter> filters) {
			super(UPLOAD_PACK
			this.uploadPackFactory = uploadPackFactory;
		}

		@Override
		protected void begin(HttpServletRequest req
				throws IOException
				ServiceNotAuthorizedException {
			UploadPack up = uploadPackFactory.create(req
			InternalHttpServerGlue.setPeerUserAgent(
					up
					req.getHeader(HDR_USER_AGENT));
			req.setAttribute(ATTRIBUTE_HANDLER
		}

		@Override
		protected void advertise(HttpServletRequest req
				PacketLineOutRefAdvertiser pck) throws IOException
				ServiceNotEnabledException
			UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
			try {
				up.setBiDirectionalPipe(false);
				up.sendAdvertisedRefs(pck);
			} finally {
				up.getRevWalk().close();
			}
		}

		@Override
		protected void respond(HttpServletRequest req
				PacketLineOut pckOut
				ServiceNotEnabledException
			UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
			try {
				up.setBiDirectionalPipe(false);
				up.sendAdvertisedRefs(new PacketLineOutRefAdvertiser(pckOut)
			} finally {
				up.getRevWalk().close();
			}
		}
	}

	static class Factory implements Filter {
		private final UploadPackFactory<HttpServletRequest> uploadPackFactory;

		Factory(UploadPackFactory<HttpServletRequest> uploadPackFactory) {
			this.uploadPackFactory = uploadPackFactory;
		}

		@Override
		public void doFilter(ServletRequest request
				FilterChain chain) throws IOException
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse rsp = (HttpServletResponse) response;
			UploadPack rp;
			try {
				rp = uploadPackFactory.create(req
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
		if (!UPLOAD_PACK_REQUEST_TYPE.equals(req.getContentType())) {
			rsp.sendError(SC_UNSUPPORTED_MEDIA_TYPE);
			return;
		}

		SmartOutputStream out = new SmartOutputStream(req
			@Override
			public void flush() throws IOException {
				doFlush();
			}
		};

		UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
		try {
			up.setBiDirectionalPipe(false);
			rsp.setContentType(UPLOAD_PACK_RESULT_TYPE);

			up.upload(getInputStream(req)
			out.close();

		} catch (ServiceMayNotContinueException e) {
			if (e.isOutput()) {
				consumeRequestBody(req);
				out.close();
			} else if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
			return;

		} catch (UploadPackInternalServerErrorException e) {
			log(up.getRepository()
			consumeRequestBody(req);
			out.close();

		} catch (Throwable e) {
			log(up.getRepository()
			if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
			return;
		}
	}

	private void log(Repository git
		getServletContext().log(MessageFormat.format(
				HttpServerText.get().internalErrorDuringUploadPack
				ServletUtils.identify(git))
	}
}
