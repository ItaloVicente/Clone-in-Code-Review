		if (ClientVersionUtil.hasChunkedRequestBug(req)) {
			GitSmartHttpTools.sendError(req
					+ HttpServerText.get().clientHas175ChunkedEncodingBug);
			return;
		}

