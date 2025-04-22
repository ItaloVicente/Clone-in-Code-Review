		int[] version = parseVersion(req.getHeader(HDR_USER_AGENT));
		if (hasChunkedEncodingRequestBug(version, req)) {
			GitSmartHttpTools.sendError(req, rsp, SC_BAD_REQUEST, "\n\n"
					+ HttpServerText.get().clientHas175ChunkedEncodingBug);
			return;
		}

