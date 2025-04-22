
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.eclipse.jgit.http.server.GitSmartHttpTools.sendError;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_REPOSITORY;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class RepositoryFilter implements Filter {
	private final RepositoryResolver<HttpServletRequest> resolver;

	private ServletContext context;

	public RepositoryFilter(RepositoryResolver<HttpServletRequest> resolver) {
		this.resolver = resolver;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
	}

	@Override
	public void destroy() {
		context = null;
	}

	@Override
	public void doFilter(final ServletRequest request
			final ServletResponse response
			throws IOException
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (request.getAttribute(ATTRIBUTE_REPOSITORY) != null) {
			context.log(MessageFormat.format(HttpServerText.get().internalServerErrorRequestAttributeWasAlreadySet
					
					
			sendError(req
			return;
		}

		String name = req.getPathInfo();
		while (name != null && 0 < name.length() && name.charAt(0) == '/')
			name = name.substring(1);
		if (name == null || name.length() == 0) {
			sendError(req
			return;
		}

		try (Repository db = resolver.open(req
			request.setAttribute(ATTRIBUTE_REPOSITORY
			chain.doFilter(request
		} catch (RepositoryNotFoundException e) {
			sendError(req
			return;
		} catch (ServiceNotEnabledException e) {
			sendError(req
			return;
		} catch (ServiceNotAuthorizedException e) {
			res.sendError(SC_UNAUTHORIZED
			return;
		} catch (ServiceMayNotContinueException e) {
			sendError(req
			return;
		} finally {
			request.removeAttribute(ATTRIBUTE_REPOSITORY);
		}
	}
}
