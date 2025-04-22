					if (ref == null)
						rev = parseSimple(rw
					else {
						rev = rw.parseAny(ref.getLeaf().getObjectId());
						ref = null;
					}
