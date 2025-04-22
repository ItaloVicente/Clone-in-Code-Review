
package org.eclipse.jgit.http.server;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_HANDLER;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SIDE_BAND;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_ERROR;
import static org.eclipse.jgit.transport.SideBandOutputStream.SMALL_BUF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.internal.transport.parser.FirstCommand;
import org.eclipse.jgit.internal.transport.parser.FirstWant;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PacketLineIn;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RequestNotYetReadException;
import org.eclipse.jgit.transport.SideBandOutputStream;
import org.eclipse.jgit.transport.UploadPack;

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

		if (isInfoRefs(req)) {
			sendInfoRefsError(req
		} else if (isUploadPack(req)) {
			sendUploadPackError(req
		} else if (isReceivePack(req)) {
			sendReceivePackError(req
		} else {
			if (httpStatus < 400)
				ServletUtils.consumeRequestBody(req);
			res.sendError(httpStatus
		}
	}

	private static void sendInfoRefsError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pck = new PacketLineOut(buf);
		String svc = req.getParameter("service");
		pck.writeString("# service=" + svc + "\n");
		pck.end();
		pck.writeString("ERR " + textForGit);
		send(req
	}

	private static void sendUploadPackError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pckOut = new PacketLineOut(buf);

		boolean sideband;
		UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
		if (up != null) {
			try {
				sideband = up.isSideBand();
			} catch (RequestNotYetReadException e) {
				sideband = isUploadPackSideBand(req);
			}
		} else
			sideband = isUploadPackSideBand(req);

		if (sideband)
			writeSideBand(buf
		else
			writePacket(pckOut
		send(req
	}

	private static boolean isUploadPackSideBand(HttpServletRequest req) {
		try {
			String line = new PacketLineIn(req.getInputStream()).readString();
			FirstWant parsed = FirstWant.fromLine(line);
			return (parsed.getCapabilities().contains(OPTION_SIDE_BAND)
					|| parsed.getCapabilities().contains(OPTION_SIDE_BAND_64K));
		} catch (IOException e) {
			return false;
		}
	}

	private static void sendReceivePackError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pckOut = new PacketLineOut(buf);

		boolean sideband;
		ReceivePack rp = (ReceivePack) req.getAttribute(ATTRIBUTE_HANDLER);
		if (rp != null) {
			try {
				sideband = rp.isSideBand();
			} catch (RequestNotYetReadException e) {
				sideband = isReceivePackSideBand(req);
			}
		} else
			sideband = isReceivePackSideBand(req);

		if (sideband)
			writeSideBand(buf
		else
			writePacket(pckOut
		send(req
	}

	private static boolean isReceivePackSideBand(HttpServletRequest req) {
		try {
			String line = new PacketLineIn(req.getInputStream()).readString();
			FirstCommand parsed = FirstCommand.fromLine(line);
			return parsed.getCapabilities().contains(CAPABILITY_SIDE_BAND_64K);
		} catch (IOException e) {
			return false;
		}
	}

	private static void writeSideBand(OutputStream out
			throws IOException {
		try (OutputStream msg = new SideBandOutputStream(CH_ERROR
				out)) {
			msg.write(Constants.encode("error: " + textForGit));
			msg.flush();
		}
	}

	private static void writePacket(PacketLineOut pckOut
			throws IOException {
		pckOut.writeString("error: " + textForGit);
	}

	private static void send(HttpServletRequest req
			String type
		ServletUtils.consumeRequestBody(req);
		res.setStatus(HttpServletResponse.SC_OK);
		res.setContentType(type);
		res.setContentLength(buf.length);
		try (OutputStream os = res.getOutputStream()) {
			os.write(buf);
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
