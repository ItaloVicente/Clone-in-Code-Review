				for (Ref ref : refs.values()) {
					ObjectId objectId = ref.getObjectId();

					if (wantAll.isEmpty()) {
						if (wantIds.contains(objectId))
							continue;
					} else {
						RevObject obj = walk.lookupOrNull(objectId);
						if (obj != null && obj.has(WANT))
							continue;
