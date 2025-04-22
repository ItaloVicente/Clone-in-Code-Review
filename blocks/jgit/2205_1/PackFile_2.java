				case Constants.OBJ_REF_DELTA: {
					readFully(pos + p
					long base = findDeltaBase(ObjectId.fromRaw(ib));
					delta = new Delta(delta

					DeltaBaseCache.Entry e = DeltaBaseCache.get(this
					if (e != null) {
						ldr = new CachedBase(e);
						break SEARCH;
					} else {
						pos = base;
						continue SEARCH;
					}
				}

				default:
					throw new IOException(MessageFormat.format(
							JGitText.get().unknownObjectType
				}
