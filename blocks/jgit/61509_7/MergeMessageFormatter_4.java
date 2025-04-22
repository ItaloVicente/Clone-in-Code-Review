			} else {
				ObjectId objectId = ref.getObjectId();
				if (objectId != null && ref.getName().equals(objectId.getName())) {
				} else {
					others.add(ref.getName());
				}
			}
