			Ref head = refdb.exactRef(Constants.HEAD);
			if (head != null) {
				ObjectId id = head.getObjectId();
				if (id != null && !id.equals(ObjectId.zeroId())) {
					locks = lockLooseRefs(pending);
					if (locks == null) {
						return;
					}
				}
