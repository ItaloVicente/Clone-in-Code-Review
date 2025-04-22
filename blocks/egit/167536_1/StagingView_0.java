		};
		stagedComposite
				.addDisposeListener(event -> stagedViewer.getLabelProvider()
						.removeListener(labelProviderChangedListener));
		stagedViewer.getLabelProvider()
				.addListener(labelProviderChangedListener);
