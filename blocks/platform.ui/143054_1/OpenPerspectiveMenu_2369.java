		}

		try {
			getWindow().getWorkbench().showPerspective(desc.getId(), getWindow(), pageInput);
		} catch (WorkbenchException e) {
			StatusUtil.handleStatus(PAGE_PROBLEMS_TITLE + ": " + e.getMessage(), e, //$NON-NLS-1$
