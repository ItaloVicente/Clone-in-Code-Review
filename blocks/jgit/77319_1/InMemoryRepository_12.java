			boolean ret = compareAndRemoveImpl(oldRef);
			if (ret) {
				objdb.markDirty();
			}
			return ret;
		}

		private boolean compareAndRemoveImpl(Ref oldRef) {
