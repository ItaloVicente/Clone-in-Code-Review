					if (wantAll.isEmpty()) {
						if (wantIds.contains(objectId))
							continue;
					} else {
						RevObject obj = rw.lookupOrNull(objectId);
						if (obj != null && obj.has(WANT))
