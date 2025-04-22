		if (wasHidden) {
			IViewPart[] stack = getSite().getPage().getViewStack(this);
			for (IViewPart vPart : stack) {
				if (vPart == part) {
					return;
				}
			}
		}
