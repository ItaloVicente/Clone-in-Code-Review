			@Override
			public String getToolTipText(Object element) {
				if (element instanceof WorkbenchPartReference) {
					WorkbenchPartReference ref = (WorkbenchPartReference) element;
					return ref.getTitleToolTip();
				}
				return super.getToolTipText(element);
			}

