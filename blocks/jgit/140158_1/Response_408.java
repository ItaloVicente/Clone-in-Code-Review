package org.eclipse.jgit.lfs.server;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_INSUFFICIENT_STORAGE;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.eclipse.jgit.lfs.lib.Constants.DOWNLOAD;
import static org.eclipse.jgit.lfs.lib.Constants.UPLOAD;
import static org.eclipse.jgit.lfs.lib.Constants.VERIFY;
import static org.eclipse.jgit.util.HttpSupport.HDR_AUTHORIZATION;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lfs.errors.LfsBandwidthLimitExceeded;
import org.eclipse.jgit.lfs.errors.LfsException;
import org.eclipse.jgit.lfs.errors.LfsInsufficientStorage;
import org.eclipse.jgit.lfs.errors.LfsRateLimitExceeded;
import org.eclipse.jgit.lfs.errors.LfsRepositoryNotFound;
import org.eclipse.jgit.lfs.errors.LfsRepositoryReadOnly;
import org.eclipse.jgit.lfs.errors.LfsUnauthorized;
import org.eclipse.jgit.lfs.errors.LfsUnavailable;
import org.eclipse.jgit.lfs.errors.LfsValidationError;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lfs.server.internal.LfsGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LfsProtocolServlet extends HttpServlet {
	private static Logger LOG = LoggerFactory
			.getLogger(LfsProtocolServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String CONTENTTYPE_VND_GIT_LFS_JSON =

	private static final int SC_RATE_LIMIT_EXCEEDED = 429;

	private static final int SC_BANDWIDTH_LIMIT_EXCEEDED = 509;

	protected abstract LargeFileRepository getLargeFileRepository(
			LfsRequest request

	protected static class LfsRequest {
		private String operation;

		private List<LfsObject> objects;

		public String getOperation() {
			return operation;
		}

		public List<LfsObject> getObjects() {
			return objects;
		}

		public boolean isUpload() {
			return operation.equals(UPLOAD);
		}

		public boolean isDownload() {
			return operation.equals(DOWNLOAD);
		}

		public boolean isVerify() {
			return operation.equals(VERIFY);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req
			throws ServletException
		Writer w = new BufferedWriter(
				new OutputStreamWriter(res.getOutputStream()

		Reader r = new BufferedReader(
				new InputStreamReader(req.getInputStream()
		LfsRequest request = LfsGson.fromJson(r
		String path = req.getPathInfo();

		res.setContentType(CONTENTTYPE_VND_GIT_LFS_JSON);
		LargeFileRepository repo = null;
		try {
			repo = getLargeFileRepository(request
					req.getHeader(HDR_AUTHORIZATION));
			if (repo == null) {
				String error = MessageFormat
						.format(LfsText.get().lfsFailedToGetRepository
				LOG.error(error);
				throw new LfsException(error);
			}
			res.setStatus(SC_OK);
			TransferHandler handler = TransferHandler
					.forOperation(request.operation
			LfsGson.toJson(handler.process()
		} catch (LfsValidationError e) {
			sendError(res
		} catch (LfsRepositoryNotFound e) {
			sendError(res
		} catch (LfsRepositoryReadOnly e) {
			sendError(res
		} catch (LfsRateLimitExceeded e) {
			sendError(res
		} catch (LfsBandwidthLimitExceeded e) {
			sendError(res
		} catch (LfsInsufficientStorage e) {
			sendError(res
		} catch (LfsUnavailable e) {
			sendError(res
		} catch (LfsUnauthorized e) {
			sendError(res
		} catch (LfsException e) {
			sendError(res
		} finally {
			w.flush();
		}
	}

	private void sendError(HttpServletResponse rsp
			String message) {
		rsp.setStatus(status);
		LfsGson.toJson(message
	}
}
