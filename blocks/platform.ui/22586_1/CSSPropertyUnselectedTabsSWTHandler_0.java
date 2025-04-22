
			CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
			if (renderer instanceof ICTabRendering) {
				if ("selected".equals(pseudo)) {
					((ICTabRendering) renderer).setActiveToolbarGradient(
							colors, percents);
				} else {
					((ICTabRendering) renderer)
					.setInactiveToolbarGradient(colors, percents);
				}
			}
