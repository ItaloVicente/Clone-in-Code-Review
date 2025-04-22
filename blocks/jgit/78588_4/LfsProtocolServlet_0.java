			if (repo == null) {
				res.setStatus(SC_SERVICE_UNAVAILABLE);
			} else {
				res.setStatus(SC_OK);
				res.setContentType(CONTENTTYPE_VND_GIT_LFS_JSON);
				TransferHandler handler = TransferHandler
						.forOperation(request.operation
				gson.toJson(handler.process()
			}
