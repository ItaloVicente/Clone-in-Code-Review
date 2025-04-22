				throw e;
			} catch (UploadPackInternalServerErrorException e) {
				log(up.getRepository(), e.getCause());
				consumeRequestBody(req);
				out.close();
			} finally {
				up.close();
