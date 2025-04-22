				switch (typeCode) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG: {
					if (sz < curs.getStreamFileThreshold())
						data = decompress(pos + p

					if (delta != null) {
						type = typeCode;
						break SEARCH;
					}

					if (data != null)
						return new ObjectLoader.SmallObject(typeCode
					else
						return new LargePackedWholeObject(typeCode
								this
				}
				case Constants.OBJ_OFS_DELTA:
				case Constants.OBJ_REF_DELTA: {
					delta = (typeCode==Constants.OBJ_OFS_DELTA)?
						objOfsDeltaFor(pos
						objRefDeltaFor(pos

					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e =
							DeltaBaseCache.get(this
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pos = delta.basePos;
					continue SEARCH;
