
			parent.setSelectedElement(candidate);
		}

		MUIElement ref = placeholder.getRef();
		if (ref != null && ref.getTags().contains(EPartService.REMOVE_ON_HIDE_TAG)) {
			parent.getChildren().remove(placeholder);
