			shell.addListener(SWT.Activate, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					MUIElement parentME = w.getParent();
					if (parentME instanceof MApplication) {
						MApplication app = (MApplication) parentME;
						app.setSelectedElement(w);
