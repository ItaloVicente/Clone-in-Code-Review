				sendError(res, w, SC_SERVICE_UNAVAILABLE,
			} else {
				res.setStatus(SC_OK);
				TransferHandler handler = TransferHandler
						.forOperation(request.operation, repo, request.objects);
				gson.toJson(handler.process(), w);
