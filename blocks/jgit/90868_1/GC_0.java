		final long unpruned = prune(Collections.<ObjectId> emptySet());
		if (automatic && unpruned > getLooseObjectLimit() && gcLog != null) {
			gcLog.write(("Even after repacking
					unpruned +
					"unpruneable loose objects").getBytes(StandardCharsets.UTF_8));
		}
