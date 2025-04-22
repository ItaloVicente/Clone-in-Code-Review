				case Constants.OBJ_REF_DELTA: {
					readFully(pos + p
					long base = findDeltaBase(ObjectId.fromRaw(ib));
					delta = new Delta(delta
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = DeltaBaseCache.get(this
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pos = base;
					continue SEARCH;
				}
