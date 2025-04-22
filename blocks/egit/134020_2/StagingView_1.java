		titleNode = null;
		titleLabelProvider = new RepositoryTreeNodeLabelProvider(false);
		titleLabelProvider.addListener(e -> {
			if (titleNode != null && form != null && !form.isDisposed()) {
				form.setText(titleLabelProvider.getStyledText(titleNode)
						.getString());
			}
		});
