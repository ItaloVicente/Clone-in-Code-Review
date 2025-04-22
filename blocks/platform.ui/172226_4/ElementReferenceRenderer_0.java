		} else // Ensure that the dispose of the element reference doesn't cascade
		if (refCtrl != null && !refCtrl.isDisposed()) {
			MPlaceholder currentRef = refElement.getCurSharedRef();
			if (currentRef == ph) {
				for (MPlaceholder aPH : refs) {
					Composite phComp = (Composite) aPH.getWidget();
					if (phComp == null || phComp.isDisposed())
						continue;
