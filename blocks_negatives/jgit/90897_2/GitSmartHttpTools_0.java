			HttpServletResponse res, String textForGit) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pck = new PacketLineOut(buf);
		String svc = req.getParameter("service");
		pck.writeString("# service=" + svc + "\n");
		pck.end();
		pck.writeString("ERR " + textForGit);
		send(req, res, infoRefsResultType(svc), buf.toByteArray());
