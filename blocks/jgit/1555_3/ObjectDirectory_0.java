		if (unpackedObjectCache.isUnpacked(objectId)) {
			ObjectLoader ldr = openObject2(curs
			if (ldr != null)
				return ldr;
			else
				unpackedObjectCache.remove(objectId);
		}

