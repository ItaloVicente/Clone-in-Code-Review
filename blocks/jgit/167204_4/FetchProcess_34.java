		final TagOpt tagopt = transport.getTagOpt();
		String getTags = (tagopt == TagOpt.NO_TAGS) ? null : Constants.R_TAGS;
		String getHead = null;
		try {
			Ref head = transport.local.exactRef(Constants.HEAD);
			if (head != null) {
				ObjectId id = head.getObjectId();
				if (id == null || id.equals(ObjectId.zeroId())) {
					getHead = Constants.HEAD;
				}
			}
		} catch (IOException e) {
		}
		conn = transport.openFetch(toFetch
