			@Override
			public Image getImage(Object element) {
				if (element instanceof WorkbenchPartReference) {
					return ((WorkbenchPartReference) element).getTitleImage();
				}
				return super.getImage(element);
			}

			@Override
			public String getToolTipText(Object element) {
				if (element instanceof WorkbenchPartReference) {
					return ((WorkbenchPartReference) element).getTitleToolTip();
				}
				return super.getToolTipText(element);
			};
		};
	}
