
			try (ObjectWalk ow = new ObjectWalk(ctx)) {
				for (Ref r : repo.getAllRefs().values()) {
					ow.markStart(ow.parseAny(r.getObjectId()));
				}
				ow.checkConnectivity();
			} catch (MissingObjectException e) {
				missingObjects.add(e.getObjectId());
			}
