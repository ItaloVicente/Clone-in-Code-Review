package org.eclipse.jgit.lfs.server.fs;

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
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

@WebServlet(asyncSupported = true)
public class FileLfsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final FileLfsRepository repository;

	private final long timeout;

	public FileLfsServlet(FileLfsRepository repository
		this.repository = repository;
		this.timeout = timeout;
	}

	@Override
	protected void doGet(HttpServletRequest req
			HttpServletResponse rsp) throws ServletException
		AnyLongObjectId obj = getObjectToTransfer(req
		if (obj != null) {
			if (repository.getSize(obj) == -1) {
				sendError(rsp
						.format(LfsServerText.get().objectNotFound
				return;
			}
			AsyncContext context = req.startAsync();
			context.setTimeout(timeout);
			rsp.getOutputStream()
					.setWriteListener(new ObjectDownloadListener(repository
							context
		}
	}

	private AnyLongObjectId getObjectToTransfer(HttpServletRequest req
			HttpServletResponse rsp) throws IOException {
		String info = req.getPathInfo();
		if (info.length() != 1 + Constants.LONG_OBJECT_ID_STRING_LENGTH) {
			sendError(rsp
					.format(LfsServerText.get().invalidPathInfo
			return null;
		}
		try {
			return LongObjectId.fromString(info.substring(1
		} catch (InvalidLongObjectIdException e) {
			sendError(rsp
			return null;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req
			HttpServletResponse rsp) throws ServletException
		AnyLongObjectId id = getObjectToTransfer(req
		if (id != null) {
			AsyncContext context = req.startAsync();
			context.setTimeout(timeout);
			req.getInputStream().setReadListener(new ObjectUploadListener(
					repository
		}
	}

	static void sendError(HttpServletResponse rsp
			throws IOException {
		rsp.setStatus(status);
		rsp.flushBuffer();
	}
}
