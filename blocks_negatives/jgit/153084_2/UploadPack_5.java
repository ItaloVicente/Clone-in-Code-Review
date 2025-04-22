					objectId = ref.getObjectId();
					if (pw.willInclude(peeledId) && !pw.willInclude(objectId)) {
						RevObject o = rw.parseAny(objectId);
						addTagChain(o, pw);
						pw.addObject(o);
					}
				}
