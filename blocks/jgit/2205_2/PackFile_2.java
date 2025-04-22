				case Constants.OBJ_REF_DELTA: {
					readFully(pos + p
					long base = findDeltaBase(ObjectId.fromRaw(ib));
					delta = new Delta(delta

					ldr = DeltaBaseCache.get(this
					if (ldr != null) {
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
