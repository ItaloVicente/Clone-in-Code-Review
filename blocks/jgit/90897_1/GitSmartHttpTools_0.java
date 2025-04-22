			HttpServletResponse res
			throws IOException {
		if (httpStatus < 400) {
			ServletUtils.consumeRequestBody(req);
		}
		res.setStatus(httpStatus);
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		writer.write(textForGit);
		writer.flush();
