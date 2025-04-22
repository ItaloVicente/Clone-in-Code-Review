		@Override
		public boolean has(AnyObjectId objectId) throws IOException {
			return (objectMap != null && objectMap.contains(objectId))
					|| ctx.has(objectId);
		}

