	private static int swtConstantToEmodelServiceConstant(int swtConstant) {
		switch (swtConstant) {
		case SWT.TOP:
			return EModelService.ABOVE;
		case SWT.BOTTOM:
			return EModelService.BELOW;
		case SWT.LEFT:
			return EModelService.LEFT_OF;
		}
		return EModelService.RIGHT_OF;
	}

	private boolean isModified(MUIElement relToElement) {
