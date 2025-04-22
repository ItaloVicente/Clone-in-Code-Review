			if (ref != null && !ref.isSymbolic()
					&& ref.getTarget().getObjectId().equals(id)) {
				currentSnapshot.setClean(otherSnapshot);
				return ref;
			}

