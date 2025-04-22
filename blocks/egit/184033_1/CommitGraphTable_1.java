		int swtStyle = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.VIRTUAL;
		if ((style & FLAG_SINGLE_SELECT) != 0) {
			swtStyle |= SWT.SINGLE;
		} else {
			swtStyle |= SWT.MULTI;
		}
		final Table rawTable = new Table(tableContainer, swtStyle);
