			Object resolved = resolve(rw
			if (resolved instanceof String) {
				return getRef((String) resolved).getLeaf().getObjectId();
			} else {
				return (ObjectId) resolved;
			}
