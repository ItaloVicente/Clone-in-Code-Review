		if (handler != null) {
			handler.receive(req
				rp.setBiDirectionalPipe(false);
				rsp.setContentType(RECEIVE_PACK_RESULT_TYPE);
				rp.receiveWithExceptionPropagation(getInputStream(req)
						null);
				out.close();
			});
		} else {
			try {
				rp.setBiDirectionalPipe(false);
				rsp.setContentType(RECEIVE_PACK_RESULT_TYPE);
				rp.receive(getInputStream(req)
				out.close();
			} catch (CorruptObjectException e ) {
				getServletContext().log(MessageFormat.format(
						HttpServerText.get().receivedCorruptObject
						e.getMessage()
						ServletUtils.identify(rp.getRepository())));
				consumeRequestBody(req);
				out.close();

			} catch (UnpackException | PackProtocolException e) {
				log(rp.getRepository()
				consumeRequestBody(req);
				out.close();

			} catch (Throwable e) {
				log(rp.getRepository()
				if (!rsp.isCommitted()) {
					rsp.reset();
					sendError(req
				}
				return;
