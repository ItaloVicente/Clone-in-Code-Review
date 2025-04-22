		if (resp != null) {
			return;
		}

		if (entity == null) {
			resp = getClient().execute(req);
			return;
		}

		try {
			if (req instanceof HttpEntityEnclosingRequest) {
				HttpEntityEnclosingRequest eReq = (HttpEntityEnclosingRequest) req;
				eReq.setEntity(entity);
			}
			resp = getClient().execute(req);
		} finally {
			entity.close();
			entity = null;
		}
