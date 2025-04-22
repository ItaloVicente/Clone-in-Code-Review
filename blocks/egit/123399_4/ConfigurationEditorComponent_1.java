
		@Override
		public Object getParent(Object object) {
			if (sectionparent != null)
				return sectionparent;
			else
				return subsectionparent;
		}
