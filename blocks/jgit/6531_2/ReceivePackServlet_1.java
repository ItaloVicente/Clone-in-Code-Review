		int[] version = parseVersion(req.getHeader(HDR_USER_AGENT));
		if (hasChunkedEncodingRequestBug(version
			GitSmartHttpTools.sendError(req
					+ HttpServerText.get().clientHas175ChunkedEncodingBug);
			return;
		}

