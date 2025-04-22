
				boolean hasCommitObject = false;
				for (ObjectId want : askFor.keySet()) {
					RevObject obj = ow.parseAny(want);
					ow.markStart(obj);
					hasCommitObject |= obj.getType() == Constants.OBJ_COMMIT;
				}
				if (hasCommitObject) {
					for (Ref ref : localRefs().values()) {
						ow.markUninteresting(ow.parseAny(ref.getObjectId()));
					}
					ow.checkConnectivity();
				}
