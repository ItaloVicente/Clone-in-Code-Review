		changeSetPreview.hide();

		changeSetPreview.refresh();
		navigatorPreview.refresh();

		generalTab.addObserver(navigatorPreview);
		textDecorationTab.addObserver(navigatorPreview);
		iconDecorationTab.addObserver(navigatorPreview);
