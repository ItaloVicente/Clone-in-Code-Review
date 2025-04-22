	static class Error {
		String message;

		Error(String m) {
			this.message = m;
		}
	}

	private void sendError(HttpServletResponse rsp
			throws IOException {
		rsp.setStatus(status);
		PrintWriter writer = rsp.getWriter();
		gson.toJson(new Error(message)
		writer.flush();
		writer.close();
		rsp.flushBuffer();
	}

	private Gson createGson() {
