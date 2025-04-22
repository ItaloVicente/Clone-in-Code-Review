
	static void sendError(int statusCode, HttpServletRequest req,
			HttpServletResponse rsp) throws IOException {
		String svc = req.getParameter("service");

		if (req.getRequestURI().endsWith("/info/refs") && isService(svc)) {
			rsp.setContentType("application/x-" + svc + "-advertisement");

			SmartOutputStream buf = new SmartOutputStream(req, rsp);
			PacketLineOut out = new PacketLineOut(buf);
			out.writeString("# service=" + svc + "\n");
			out.end();
			out.writeString("ERR " + translate(statusCode));
			buf.close();
			return;
		}

		String accept = req.getHeader(HDR_ACCEPT);
		if (accept != null && accept.contains(UploadPackServlet.RSP_TYPE)) {
			rsp.setContentType(UploadPackServlet.RSP_TYPE);
			SmartOutputStream buf = new SmartOutputStream(req, rsp);
			PacketLineOut out = new PacketLineOut(buf);
			out.writeString("ERR " + translate(statusCode));
			buf.close();
			return;
		}

		rsp.sendError(statusCode);
	}

	private static boolean isService(String svc) {
		return "git-upload-pack".equals(svc) || "git-receive-pack".equals(svc);
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
