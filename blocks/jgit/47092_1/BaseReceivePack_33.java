				if (o instanceof RevBlob && !db.hasObject(o))
					throw new MissingObjectException(o
			}
			checking.endTask();

			if (baseObjects != null) {
				for (ObjectId id : baseObjects) {
					o = ow.parseAny(id);
					if (!o.has(RevFlag.UNINTERESTING))
						throw new MissingObjectException(o
				}
