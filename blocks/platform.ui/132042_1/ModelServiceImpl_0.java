	static class CacheKey {
		final String id;
		final MUIElement searchRoot;
		final int hashCode = Integer.MIN_VALUE;

		CacheKey(String id, MUIElement searchRoot) {
			if (id == null || searchRoot == null) {
				throw new IllegalArgumentException();
			}
			this.id = id;
			this.searchRoot = searchRoot;

			final int prime = 31;
			int hashCode = 1;
			hashCode = prime * hashCode + ((id == null) ? 0 : id.hashCode());
			hashCode = prime * hashCode + ((searchRoot == null) ? 0 : searchRoot.hashCode());
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (!id.equals(other.id))
				return false;
			if (!searchRoot.equals(other.searchRoot))
				return false;
			return true;
		}
	}


