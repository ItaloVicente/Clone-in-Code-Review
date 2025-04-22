				LabelProvider labelProvider = new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((MPart) element).getLocalizedLabel();
					}
				};
