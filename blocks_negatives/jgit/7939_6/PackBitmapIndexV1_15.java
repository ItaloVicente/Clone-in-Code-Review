		@Override
		public boolean equals(Object o) {
			if (o instanceof PackBitmapIndexImpl)
				return getPackIndex()
						== ((PackBitmapIndexImpl) o).getPackIndex();
			return false;
		}
