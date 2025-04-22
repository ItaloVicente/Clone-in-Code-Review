				if (partToTag.getTags().contains(IPresentationEngine.ORIENTATION_HORIZONTAL)) {
					horizontalItem.setSelection(true);
				} else if (partToTag.getTags().contains(IPresentationEngine.ORIENTATION_VERTICAL)) {
					verticalItem.setSelection(true);
				} else {
					defaultItem.setSelection(true);
