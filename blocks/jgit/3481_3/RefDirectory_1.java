			if (ref != null && ref.isSymbolic()
					&& ref.getTarget().getName().equals(target)) {
				currentSnapshot.setClean(otherSnapshot);
				return ref;
			}
			return newSymbolicRef(path.lastModified()
