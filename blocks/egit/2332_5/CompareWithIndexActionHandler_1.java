			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, null);
			CompareUI.openCompareEditor(in);
		} else {
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().showView(
								CompareTreeView.ID);
				view.setInput(resources, CompareTreeView.INDEX_VERSION);
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		}
