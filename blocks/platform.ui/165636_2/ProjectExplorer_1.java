
					StructuredSelection projectSelection = new StructuredSelection(selectedProjects);
					WorkspaceAction action = null;
					if (allOpen) {
						action = new CloseResourceAction(() -> shell);
					}
					if (allClosed) {
						action = new OpenResourceAction(() -> shell);
