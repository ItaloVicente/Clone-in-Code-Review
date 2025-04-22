
			viewId = getLegacyViewId(marker);
			if (viewId != null) {
				if (returnValue)
					view = page.findView(viewId);
				else
					view = showView ? page.showView(viewId) : page
							.findView(viewId);
			}
