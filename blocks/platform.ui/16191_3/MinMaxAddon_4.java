			MPlaceholder eaPlaceholder = (MPlaceholder) modelService.find(ID_EDITOR_AREA, curPersp);
			adjustCTFButtons(eaPlaceholder);
		}

		if (event.getProperty(EventTags.OLD_VALUE) instanceof MPerspective) {
			MPerspective oldPersp = (MPerspective) event.getProperty(EventTags.OLD_VALUE);
			String perspId = '(' + oldPersp.getElementId() + ')';
			for (MToolControl tc : tcList) {
				if (tc.getObject() instanceof TrimStack && tc.getElementId().contains(perspId)) {
					TrimStack ts = (TrimStack) tc.getObject();
					ts.showStack(false);
					tc.setToBeRendered(false);
