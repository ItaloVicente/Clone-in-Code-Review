			ObjectId id = ref.getObjectId();
			if (id != null) {
				ids.add(id);
			}
			id = ref.getPeeledObjectId();
			if (id != null) {
				ids.add(id);
			}
