		if (idx == -1) {
			MUIElement spacer = modelService.find(WorkbenchWindow.PERSPECTIVE_SPACER_ID, trimBar);
			if (spacer != null) {
				idx = trimBar.getChildren().indexOf(spacer);
			}
		}

