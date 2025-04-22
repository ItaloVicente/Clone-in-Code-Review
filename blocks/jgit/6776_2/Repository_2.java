			Object resolved = resolve(rw
			if (resolved instanceof Ref) {
				return ((Ref) resolved).getObjectId();
			} else {
				return (ObjectId) resolved;
			}
