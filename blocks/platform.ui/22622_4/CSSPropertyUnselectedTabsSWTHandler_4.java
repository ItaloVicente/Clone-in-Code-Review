			Color[] colors = null;
			int[] percents = null;
			if (!grad.getValues().isEmpty()) {
				colors = CSSSWTColorHelper.getSWTColors(grad,
						control.getDisplay(), engine);
				percents = CSSSWTColorHelper.getPercents(grad);
			}
			if (isUnselectedTabsColorProp(property)) {
				((ICTabRendering) renderer).setUnselectedTabsColor(colors,
						percents);
