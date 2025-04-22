			ref[0] = r;
			if (r != null) {
				if (r.isSymbolic()) {
					name = r.getTarget().getName();
				}
				head = r.getObjectId();
				if (head != null) {
					if (name == null) {
						name = head.name();
					}
				} else {
					head = ObjectId.zeroId();
