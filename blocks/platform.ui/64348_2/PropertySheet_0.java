			IWorkbenchPartSite site = getSite();
			IWorkbenchPage page = site.getPage();
			IViewPart[] stack = page.getViewStack(this);
			if (stack != null) {
				for (IViewPart vPart : stack) {
					if (vPart == part) {
						return;
					}
