			shell.addListener(SWT.Activate, event -> {
				MUIElement parentME = w.getParent();
				if (parentME instanceof MApplication) {
					MApplication app = (MApplication) parentME;
					app.setSelectedElement(w);
					w.getContext().activate();
				} else if (parentME == null) {
					parentME = modelService.getContainer(w);
					if (parentME instanceof MContext) {
