		try {
			IViewPart view = page.showView(IPageLayout.ID_RES_NAV);
			if (view instanceof ISetSelectionTarget) {
				ISelection selection = new StructuredSelection(v);
				((ISetSelectionTarget) view).selectReveal(selection);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(page.getWorkbenchWindow().getShell(),
					ResourceNavigatorMessages.ShowInNavigator_errorMessage, e.getMessage(), e.getStatus());
		}
	}
