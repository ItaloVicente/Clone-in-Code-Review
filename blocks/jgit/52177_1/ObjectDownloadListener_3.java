package org.eclipse.jgit.lfs.server;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.eclipse.jgit.lfs.errors.InvalidLongObjectIdException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.LargeObjectRepository;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

@WebServlet(name = "LargeObjectServlet"
public class LargeObjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private LargeObjectRepository repository;

	private long timeout;

	public LargeObjectServlet(LargeObjectRepository repository
		this.repository = repository;
		this.timeout = timeout;
	}

	@Override
	protected void doGet(HttpServletRequest request
			HttpServletResponse response) throws ServletException
		AnyLongObjectId obj = getObjectToTransfer(request
		if (obj != null && exists(response
			AsyncContext context = request.startAsync();
			context.setTimeout(timeout);
			response.getOutputStream()
					.setWriteListener(new ObjectDownloadListener(repository
							context
		}
	}

	private AnyLongObjectId getObjectToTransfer(HttpServletRequest request
			HttpServletResponse response) throws IOException {
		String info = request.getPathInfo();
		if (info.length() != 65) {
			response.sendError(HttpStatus.SC_BAD_REQUEST
					.format(LfsServerText.get().invalidPathInfo
			return null;
		}
		try {
			return LongObjectId.fromString(info.substring(1
		} catch (InvalidLongObjectIdException e) {
			response.sendError(HttpStatus.SC_BAD_REQUEST
			return null;
		}
	}

	private boolean exists(HttpServletResponse response
			throws IOException {
		if (!repository.exists(obj)) {
			response.sendError(HttpStatus.SC_NOT_FOUND
					.format(LfsServerText.get().objectNotFound
			return false;
		}
		return true;
	}

	@Override
	protected void doPut(HttpServletRequest request
			HttpServletResponse response) throws ServletException
		AnyLongObjectId id = getObjectToTransfer(request
		if (id != null) {
			AsyncContext context = request.startAsync();
			context.setTimeout(timeout);
			request.getInputStream().setReadListener(new ObjectUploadListener(
					repository
		}
	}
}
