	private void defaultUploadPackHandler(HttpServletRequest req
			HttpServletResponse rsp
		try {
			r.upload();
		} catch (ServiceMayNotContinueException e) {
			if (!e.isOutput() && !rsp.isCommitted()) {
				rsp.reset();
				sendError(req
			}
