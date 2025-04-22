		if (element instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) element;
			if (ph.getRef() != null && ph.getRef().getCurSharedRef() == ph) {
				ph.getRef().setCurSharedRef(null);
			}
		}

