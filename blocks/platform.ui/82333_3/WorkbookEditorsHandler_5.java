			@Override
			public Image getImage(Object element) {
				if (element instanceof EditorReference) {
					return ((EditorReference) element).getTitleImage();
				}
				return super.getImage(element);
