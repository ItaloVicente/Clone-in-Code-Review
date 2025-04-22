
			try {
				up.uploadWithExceptionPropagation(getInputStream(req), out,
						null);
				out.close();
			} catch (ServiceMayNotContinueException e) {
				if (e.isOutput()) {
					consumeRequestBody(req);
					out.close();
				}
				throw e;
			} catch (UploadPackInternalServerErrorException e) {
				log(up.getRepository(), e.getCause());
