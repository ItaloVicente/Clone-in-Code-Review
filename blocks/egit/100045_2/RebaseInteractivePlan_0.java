
		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class adapter) {
			if (adapter.isInstance(this)) {
				return this;
			} else if (Repository.class.equals(adapter)) {
				return repositoryRef.get();
			}
			return null;
		}
