	private ColumnLabelProvider createNameLabelProvider() {
		return new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof Ref) {
					String name = ((Ref) element).getName();
					return name.substring(prefix.length());
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				return RepositoryTreeNodeType.REF.getIcon();
			}

		};
	}

