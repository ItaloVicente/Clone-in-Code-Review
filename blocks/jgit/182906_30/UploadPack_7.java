						ObjectId peeledId = ref.getPeeledObjectId();
						objectId = ref.getObjectId();
						if (peeledId == null || objectId == null)
							continue;

						objectId = ref.getObjectId();
						if (pw.willInclude(peeledId) && !pw.willInclude(objectId)) {
							RevObject o = rw.parseAny(objectId);
							addTagChain(o
							pw.addObject(o);
						}
