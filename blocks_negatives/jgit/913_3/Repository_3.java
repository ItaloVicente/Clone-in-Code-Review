						ref = mapObject(refId, null);
						if (ref instanceof Commit) {
							final ObjectId parents[] = ((Commit) ref)
									.getParentIds();
							if (parents.length == 0)
								refId = null;
