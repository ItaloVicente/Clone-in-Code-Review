
			try (ObjectWalk ow = new ObjectWalk(ctx)) {
				for (Ref r : repo.getAllRefs().values()) {
					try {
						RevObject tip = ow.parseAny(r.getObjectId());
						ow.markStart(tip);
						ow.checkConnectivity();
						ow.markUninteresting(tip);
					} catch (MissingObjectException e) {
						errors.getMissingObjects().add(e.getObjectId());
					}
				}
			}
