			CTabFolder folder = ((CTabFolder) control);
			Color[] colors = CSSSWTColorHelper.getSWTColors(grad, folder.getDisplay(), engine);
			int[] percents = CSSSWTColorHelper.getPercents(grad);
			folder.setBackground(colors, percents, true);

			CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
			if (renderer instanceof ICTabRendering) {
				if ("selected".equals(pseudo)) {
					((ICTabRendering) renderer).setActiveToolbarGradient(
							colors, percents);
				} else {
					((ICTabRendering) renderer)
					.setInactiveToolbarGradient(colors, percents);
				}
