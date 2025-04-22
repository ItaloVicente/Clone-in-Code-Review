				up.setBiDirectionalPipe(false);
				rsp.setContentType(UPLOAD_PACK_RESULT_TYPE);

				try {
					up.uploadWithExceptionPropagation(getInputStream(req)
							null);
				} catch (ServiceMayNotContinueException e) {
					if (e.isOutput()) {
						consumeRequestBody(req);
					}
					throw e;
				} catch (UploadPackInternalServerErrorException e) {
					log(up.getRepository()
