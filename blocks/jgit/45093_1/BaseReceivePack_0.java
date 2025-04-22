		try (final ObjectWalk ow = new ObjectWalk(db)) {
			ow.setRetainBody(false);
			if (baseObjects != null) {
				ow.sort(RevSort.TOPO);
				if (!baseObjects.isEmpty())
					ow.sort(RevSort.BOUNDARY
