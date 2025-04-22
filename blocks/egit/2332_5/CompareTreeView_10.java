		@Override
		public Object[] getElements(Object inputElement) {
			if (rightPathsWithChildren.isEmpty() && rightOnly.isEmpty())
				return new String[] { UIText.CompareTreeView_NoDifferencesFoundMessage };
			return super.getElements(inputElement);
		}

