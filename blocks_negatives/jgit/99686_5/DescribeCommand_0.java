			Map<ObjectId, Ref> tags = new HashMap<>();

			for (Ref r : repo.getRefDatabase().getRefs(R_TAGS).values()) {
				ObjectId key = repo.peel(r).getPeeledObjectId();
				if (key == null)
					key = r.getObjectId();
				tags.put(key, r);
			}
