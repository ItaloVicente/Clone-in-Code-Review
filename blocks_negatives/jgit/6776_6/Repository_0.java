						if (ref.getObjectId() == null)
							return null;
					} else
						ref = getRef(refName);
					if (ref == null)
						return null;
					rev = resolveReflog(rw, ref, time);
