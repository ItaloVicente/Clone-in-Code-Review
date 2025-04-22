		if (trimmedPartComposite.getLayout() instanceof TrimmedPartLayout) {
			TrimmedPartLayout layout = (TrimmedPartLayout) trimmedPartComposite.getLayout();
			CSSEngineHelper helper = new CSSEngineHelper(localContext, trimmedPartComposite);

			layout.gutterTop = helper.getMarginTop(0);
			layout.gutterBottom = helper.getMarginBottom(0);
			layout.gutterLeft = helper.getMarginLeft(0);
			layout.gutterRight = helper.getMarginRight(0);
		}

		IPresentationEngine renderer = context.get(IPresentationEngine.class);
		for (MTrimBar trimBar : part.getTrimBars()) {
			renderer.createGui(trimBar, trimmedPartComposite, part.getContext());
			if (!trimBar.isVisible()) {
				trimBar.setVisible(true);
				trimBar.setVisible(false);
			}
		}

		return trimmedPartComposite;
