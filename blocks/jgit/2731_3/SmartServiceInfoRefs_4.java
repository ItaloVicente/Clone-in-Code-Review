	private void service(ServletRequest request
			throws IOException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse rsp = (HttpServletResponse) response;
		try {
			rsp.setContentType("application/x-" + svc + "-advertisement");

			final SmartOutputStream buf = new SmartOutputStream(req
			final PacketLineOut out = new PacketLineOut(buf);
			out.writeString("# service=" + svc + "\n");
			out.end();
			advertise(req
			buf.close();
		} catch (ServiceNotAuthorizedException e) {
			rsp.sendError(SC_UNAUTHORIZED);

		} catch (ServiceNotEnabledException e) {
			rsp.sendError(SC_FORBIDDEN);
		}
	}

	protected abstract void begin(HttpServletRequest req
			throws IOException
			ServiceNotAuthorizedException;

	protected abstract void advertise(HttpServletRequest req
