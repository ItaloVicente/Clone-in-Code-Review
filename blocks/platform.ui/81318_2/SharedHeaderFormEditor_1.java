			activationListener = event -> {
				boolean wasHeaderActive = event.widget != getContainer();

				int activePage = getActivePage();
				if (SharedHeaderFormEditor.this.wasHeaderActive != wasHeaderActive && activePage != -1
						&& pages.get(activePage) instanceof IEditorPart) {
