			ObjectId id = r.getPeeledObjectId();
			if (id == null)
				id = r.getObjectId();
			if (id == null)
				continue;
			parseReachable(id);
