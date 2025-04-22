				if (event.getNewValue() != null) {
					fireLabelProviderChanged(new LabelProviderChangedEvent(
							PresentationLabelProvider.this));
				} else {
					tree.getViewer().setContentProvider(new ThemeContentProvider());
					tree.getViewer().collapseAll();
				}
