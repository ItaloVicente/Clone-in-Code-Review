		Layout layout = s.getLayout();
		if (layout instanceof SashLayout) {
			if (((SashLayout) layout).layoutUpdateInProgress) {
				return;
			}
		}
		s.layout(true, true);
