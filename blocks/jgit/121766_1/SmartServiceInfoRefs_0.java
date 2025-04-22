	protected void respond(HttpServletRequest req
			PacketLineOut pckOut
			throws IOException
			ServiceNotAuthorizedException {
		pckOut.writeString("# service=" + svc + "\n");
		pckOut.end();
		advertise(req
	}

