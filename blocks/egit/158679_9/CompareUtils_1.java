				if (!(input instanceof GitCompareFileRevisionEditorInput)) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(
							() -> openCompareToolInternal(workbenchPage,
									input));
					return Status.OK_STATUS;
				}
