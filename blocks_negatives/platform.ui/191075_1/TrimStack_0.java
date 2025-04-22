		if (trimStackTB == null) {
			return null;
		}

		Shell theShell = trimStackTB.getShell();
		if (theShell.getLayout() instanceof TrimmedPartLayout) {
			TrimmedPartLayout tpl = (TrimmedPartLayout) theShell.getLayout();
			if (!tpl.clientArea.isDisposed()) {
				return tpl.clientArea;
			}
