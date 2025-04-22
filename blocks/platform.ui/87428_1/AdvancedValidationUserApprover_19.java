		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			status[0] = computeOperationStatus(operation, history, uiInfo,
					doing);

			if (!status[0].isOK()) {
				status[0] = reportAndInterpretStatus(status[0], uiInfo,
						operation, doing);
