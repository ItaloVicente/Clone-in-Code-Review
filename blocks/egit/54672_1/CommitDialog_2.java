		if (!getSeverityItems().isEmpty()) {
			int warnings = 0;
			int errors = 0;
			if ((getSeverityItems().get(IMarker.SEVERITY_WARNING) != null)) {
				warnings = getSeverityItems().get(IMarker.SEVERITY_WARNING)
						.size();
			}

			if (getSeverityItems().get(IMarker.SEVERITY_ERROR) != null) {
				errors = getSeverityItems().get(IMarker.SEVERITY_ERROR).size();
			}
			CommitWarningDialog dialog = new CommitWarningDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					warnings, errors);
			if (dialog.open() == Window.CANCEL) {
				return;
			}
		}

