
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PacketLineOut;

public class GitSmartHttpTools {
	private static final String INFO_REFS = Constants.INFO_REFS;

	public static final String UPLOAD_PACK = "git-upload-pack";

	public static final String RECEIVE_PACK = "git-receive-pack";

	public static final String UPLOAD_PACK_REQUEST_TYPE =
			"application/x-git-upload-pack-request";

	public static final String UPLOAD_PACK_RESULT_TYPE =
			"application/x-git-upload-pack-result";

	public static final String RECEIVE_PACK_REQUEST_TYPE =
			"application/x-git-receive-pack-request";

	public static final String RECEIVE_PACK_RESULT_TYPE =
			"application/x-git-receive-pack-result";

	public static final List<String> VALID_SERVICES =
			Collections.unmodifiableList(Arrays.asList(new String[] {
					UPLOAD_PACK

	private static final String INFO_REFS_PATH = "/" + INFO_REFS;
	private static final String UPLOAD_PACK_PATH = "/" + UPLOAD_PACK;
	private static final String RECEIVE_PACK_PATH = "/" + RECEIVE_PACK;

	private static final List<String> SERVICE_SUFFIXES =
			Collections.unmodifiableList(Arrays.asList(new String[] {
					INFO_REFS_PATH

	public static boolean isGitClient(HttpServletRequest req) {
		return isInfoRefs(req) || isUploadPack(req) || isReceivePack(req);
	}

	public static void sendError(HttpServletRequest req
			HttpServletResponse res
		sendError(req
	}

	public static void sendError(HttpServletRequest req
			HttpServletResponse res
			throws IOException {
		if (textForGit == null || textForGit.length() == 0) {
			switch (httpStatus) {
			case SC_FORBIDDEN:
				textForGit = HttpServerText.get().repositoryAccessForbidden;
				break;
			case SC_NOT_FOUND:
				textForGit = HttpServerText.get().repositoryNotFound;
				break;
			case SC_INTERNAL_SERVER_ERROR:
				textForGit = HttpServerText.get().internalServerError;
				break;
			default:
				textForGit = "HTTP " + httpStatus;
				break;
			}
		}

		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pck = new PacketLineOut(buf);

		if (isInfoRefs(req)) {
			String svc = req.getParameter("service");
			pck.writeString("# service=" + svc + "\n");
			pck.end();
			pck.writeString("ERR " + textForGit);
			send(res
		} else if (isUploadPack(req)) {
			pck.writeString("ERR " + textForGit);
			send(res
		} else if (isReceivePack(req)) {
			pck.writeString("ERR " + textForGit);
			send(res
		} else {
			res.sendError(httpStatus);
		}
	}

	private static void send(HttpServletResponse res
			throws IOException {
		res.setStatus(HttpServletResponse.SC_OK);
		res.setContentType(type);
		res.setContentLength(buf.length);
		ServletOutputStream os = res.getOutputStream();
		try {
			os.write(buf);
		} finally {
			os.close();
		}
	}

	public static String getResponseContentType(HttpServletRequest req) {
		if (isInfoRefs(req))
			return infoRefsResultType(req.getParameter("service"));
		else if (isUploadPack(req))
			return UPLOAD_PACK_RESULT_TYPE;
		else if (isReceivePack(req))
			return RECEIVE_PACK_RESULT_TYPE;
		else
			throw new IllegalArgumentException();
	}

	static String infoRefsResultType(String svc) {
		return "application/x-" + svc + "-advertisement";
	}

	public static String stripServiceSuffix(String path) {
		for (String suffix : SERVICE_SUFFIXES) {
			if (path.endsWith(suffix))
				return path.substring(0
		}
		return path;
	}

	public static boolean isInfoRefs(HttpServletRequest req) {
		return req.getRequestURI().endsWith(INFO_REFS_PATH)
				&& VALID_SERVICES.contains(req.getParameter("service"));
	}

	public static boolean isUploadPack(String pathOrUri) {
		return pathOrUri != null && pathOrUri.endsWith(UPLOAD_PACK_PATH);
	}

	public static boolean isUploadPack(HttpServletRequest req) {
		return isUploadPack(req.getRequestURI())
				&& UPLOAD_PACK_REQUEST_TYPE.equals(req.getContentType());
	}

	public static boolean isReceivePack(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return uri != null && uri.endsWith(RECEIVE_PACK_PATH)
				&& RECEIVE_PACK_REQUEST_TYPE.equals(req.getContentType());
	}

	private GitSmartHttpTools() {
	}
}
