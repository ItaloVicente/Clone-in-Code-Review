			up.setBiDirectionalPipe(false);
			rsp.setContentType(UPLOAD_PACK_RESULT_TYPE);

			try {
				up.uploadWithExceptionPropagation(getInputStream(req), out,
						null);
				out.close();
			} catch (ServiceMayNotContinueException e) {
				if (e.isOutput()) {
