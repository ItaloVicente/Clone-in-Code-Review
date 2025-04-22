
		protected ObjectId resolveObjectId(Ref ref) {
			if (ref == null)
				return null;
			if (ref.isPeeled())
				return ref.getObjectId();
			else
				return ref.getPeeledObjectId();
		}
