
		reply(rsp, out.toByteArray());
	}

	private void reply(final HttpServletResponse rsp, final byte[] result)
			throws IOException {
		rsp.setContentType(RSP_TYPE);
		rsp.setContentLength(result.length);
		final OutputStream os = rsp.getOutputStream();
		try {
			os.write(result);
			os.flush();
		} finally {
			os.close();
		}
