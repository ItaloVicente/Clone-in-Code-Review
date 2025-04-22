		if (resp == null)
			if (entity != null) {
				if (req instanceof HttpEntityEnclosingRequest) {
					HttpEntityEnclosingRequest eReq = (HttpEntityEnclosingRequest) req;
					eReq.setEntity(entity);
				}
				resp = getClient().execute(req);
				entity.getBuffer().close();
				entity = null;
			} else
				resp = getClient().execute(req);
