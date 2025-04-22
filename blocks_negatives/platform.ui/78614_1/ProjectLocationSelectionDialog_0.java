			String nameSegment;
			if (counter > 1) {
				nameSegment = NLS.bind(IDEWorkbenchMessages.CopyProjectAction_copyNameTwoArgs, counter, projectName);
			} else {
				nameSegment = NLS.bind(
						IDEWorkbenchMessages.CopyProjectAction_copyNameOneArg,
						projectName);
