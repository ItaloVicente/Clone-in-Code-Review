				if (CompareUtils.canDirectlyOpenInCompare(baseFile)) {
					showSingleFileComparison(baseFile, dlg.getRefName());
				} else {
					synchronizeModel(baseFile, repo, dlg.getRefName());
				}
			} else {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(resources, dlg.getRefName());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			}
		}
		return null;
	}
