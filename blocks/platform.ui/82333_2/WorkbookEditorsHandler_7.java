
			@Override
			public String getToolTipText(Object element) {
				if (element instanceof EditorReference) {
					return ((EditorReference) element).getTitleToolTip();
				}
				return super.getToolTipText(element);
			};
		};
