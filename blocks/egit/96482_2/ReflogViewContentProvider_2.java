		@Override
		public Object[] getChildren(Object o) {
			if (refLog != null) {
				return refLog.toArray();
			}
			return super.getChildren(o);
