		stagedViewer = createTree(stagedComposite);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(stagedViewer.getControl());
		stagedViewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
		IBaseLabelProvider labelProvider = createLabelProvider(stagedViewer);
		labelProvider.addListener(new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				updateCommitButtons();
			}
