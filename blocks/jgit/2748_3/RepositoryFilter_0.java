
	static void sendError(int statusCode
			HttpServletResponse rsp) throws IOException {
		String svc = req.getParameter("service");
		String accept = req.getHeader(HDR_ACCEPT);

		if (svc != null && svc.startsWith("git-") && accept != null
				&& accept.contains("application/x-" + svc + "-advertisement")) {
			rsp.setContentType("application/x-" + svc + "-advertisement");

			SmartOutputStream buf = new SmartOutputStream(req
			PacketLineOut out = new PacketLineOut(buf);
			out.writeString("# service=" + svc + "\n");
			out.end();
			out.writeString("ERR " + translate(statusCode));
			buf.close();
			return;
		}

		if (accept != null && accept.contains(UploadPackServlet.RSP_TYPE)) {
			rsp.setContentType(UploadPackServlet.RSP_TYPE);
			SmartOutputStream buf = new SmartOutputStream(req
			PacketLineOut out = new PacketLineOut(buf);
			out.writeString("ERR " + translate(statusCode));
			buf.close();
			return;
		}

		rsp.sendError(statusCode);
	}

	private static String translate(int statusCode) {
		switch (statusCode) {
		case SC_NOT_FOUND:
			return HttpServerText.get().repositoryNotFound;

		case SC_FORBIDDEN:
			return HttpServerText.get().repositoryAccessForbidden;

		default:
			return String.valueOf(statusCode);
		}
	}
