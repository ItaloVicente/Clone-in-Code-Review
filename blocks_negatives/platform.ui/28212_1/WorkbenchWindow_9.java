				@Override
				public boolean changed(IEclipseContext context) {
					ExpressionInfo info = new ExpressionInfo();
					IEclipseContext leafContext = windowContext.getActiveLeaf();
					MenuManagerRendererFilter.collectInfo(info, mainMenu, renderer, leafContext,
							true);
					for (String name : info.getAccessedVariableNames()) {
						leafContext.get(name);
					}
					if (canUpdateMenus && workbench.getDisplay() != null) {
						canUpdateMenus = false;
						workbench.getDisplay().asyncExec(menuUpdater);
