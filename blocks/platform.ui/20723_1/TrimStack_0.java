			if (use_overlays) {
				hostPane = getHostPane();
				ctrl.setParent(hostPane);
				clientAreaComposite.addControlListener(caResizeListener);

				setPaneLocation(hostPane);
