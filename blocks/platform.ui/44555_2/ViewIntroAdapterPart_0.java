		if (introModelPart.getCurSharedRef() != null) {
			MUIElement introPartParent = introModelPart.getCurSharedRef().getParent();
			if (introPartParent instanceof MPartStack) {
				return (MPartStack) introPartParent;
			}
		}
