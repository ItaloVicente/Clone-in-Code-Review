		final Object[] ret = new Object[1];
		BusyIndicator.showWhile(null, () -> {
			try {
				ret[0] = busyShowPerspective(perspectiveId, targetWindow, input);
			} catch (WorkbenchException e) {
				ret[0] = e;
			}
		});
		if (ret[0] instanceof IWorkbenchPage) {
			return (IWorkbenchPage) ret[0];
		} else if (ret[0] instanceof WorkbenchException) {
			throw ((WorkbenchException) ret[0]);
		} else {
			throw new WorkbenchException(WorkbenchMessages.WorkbenchPage_AbnormalWorkbenchCondition);
		}
	}

	private IWorkbenchPage busyShowPerspective(String perspectiveId, IWorkbenchWindow targetWindow, IAdaptable input)
			throws WorkbenchException {
		Assert.isNotNull(perspectiveId);
