			try (ObjectWalk ow = new ObjectWalk(ctx)) {
				for (Ref r : repo.getAllRefs().values()) {
					try {
						RevObject tip = ow.parseAny(r.getObjectId());
						if (r.getLeaf().getName().startsWith(Constants.R_HEADS)) {
							if (tip.getType() != Constants.OBJ_COMMIT) {
								errors.getNonCommitHeads()
										.add(r.getLeaf().getName());
							}
						}
						ow.markStart(tip);
						ow.checkConnectivity();
						ow.markUninteresting(tip);
					} catch (MissingObjectException e) {
						errors.getMissingObjects().add(e.getObjectId());
					}
				}
			}
