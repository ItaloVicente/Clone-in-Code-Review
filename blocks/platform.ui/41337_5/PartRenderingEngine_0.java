		if (element instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) element;
			if (ph.getRef().getCurSharedRef() == ph) {
				ph.getRef().setCurSharedRef(null);
			}
		}

