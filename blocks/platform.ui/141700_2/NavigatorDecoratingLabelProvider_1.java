
		@Override
		public String getToolTipText(Object element) {
			if (provider instanceof IToolTipProvider) {
				return ((IToolTipProvider) provider).getToolTipText(element);
			}
			return null;
		}
