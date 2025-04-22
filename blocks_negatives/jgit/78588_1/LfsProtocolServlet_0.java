		if (repo == null) {
			res.setStatus(SC_SERVICE_UNAVAILABLE);
			return;
		}

		res.setStatus(SC_OK);
		res.setContentType(CONTENTTYPE_VND_GIT_LFS_JSON);
		TransferHandler handler = TransferHandler
				.forOperation(request.operation, repo, request.objects);
		gson.toJson(handler.process(), w);
		w.flush();
