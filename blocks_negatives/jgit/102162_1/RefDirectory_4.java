					Ref packedRef = cur.get(refName);
					ObjectId clr_oid = currentLooseRef.getObjectId();
					if (clr_oid != null
							&& clr_oid.equals(packedRef.getObjectId())) {
						RefList<LooseRef> curLoose, newLoose;
						do {
							curLoose = looseRefs.get();
							int idx = curLoose.find(refName);
							if (idx < 0) {
								break;
							}
							newLoose = curLoose.remove(idx);
						} while (!looseRefs.compareAndSet(curLoose, newLoose));
						int levels = levelsIn(refName) - 2;
						delete(refFile, levels, rLck);
