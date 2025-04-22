			boolean ret = compareAndPutImpl(oldRef
			if (ret) {
				objdb.markDirty();
			}
			return ret;
		}

		private boolean compareAndPutImpl(Ref oldRef
				throws IOException {
