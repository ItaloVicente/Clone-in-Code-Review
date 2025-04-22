		@Override
		public Object[] getElements(Object inputElement) {
			if (leftPathsWithChildren.isEmpty() && leftOnly.isEmpty())
				return new String[] { UIText.CompareTreeView_NoDifferencesFoundMessage };
			return super.getElements(inputElement);
		}

