		try {
			rp.setBiDirectionalPipe(false);
			rsp.setContentType(RECEIVE_PACK_RESULT_TYPE);

			rp.receive(getInputStream(req), out, null);
			out.close();
		} catch (CorruptObjectException e ) {
			getServletContext().log(MessageFormat.format(
					HttpServerText.get().receivedCorruptObject,
					e.getMessage(),
					ServletUtils.identify(rp.getRepository())));
			consumeRequestBody(req);
			out.close();

		} catch (UnpackException | PackProtocolException e) {
			log(rp.getRepository(), e.getCause());
			consumeRequestBody(req);
			out.close();

		} catch (Throwable e) {
			log(rp.getRepository(), e);
			if (!rsp.isCommitted()) {
				rsp.reset();
				sendError(req, rsp, SC_INTERNAL_SERVER_ERROR);
