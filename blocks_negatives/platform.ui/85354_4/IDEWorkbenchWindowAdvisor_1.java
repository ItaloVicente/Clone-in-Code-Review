				lastEditorTitleTooltip = activeEditor.getTitleToolTip();
				title = NLS.bind(
						IDEWorkbenchMessages.WorkbenchWindow_shellTitle,
						lastEditorTitleTooltip, title);
			}
			IPerspectiveDescriptor persp = currentPage.getPerspective();
			if (persp != null) {
				label = persp.getLabel();
			}
			IAdaptable input = currentPage.getInput();
			if (input != null && !input.equals(wbAdvisor.getDefaultPageInput())) {
				label = currentPage.getLabel();
			}
				title = NLS.bind(
						IDEWorkbenchMessages.WorkbenchWindow_shellTitle, label,
						title);
