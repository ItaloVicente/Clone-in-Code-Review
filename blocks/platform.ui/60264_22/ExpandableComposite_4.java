			int width = 0;
			if (wHint == SWT.DEFAULT) {
				if (labelDefault.x > 0)
					width = labelDefault.x;
				if (tcDefault.x > 0)
					width += IGAP + tcDefault.x;
				if (toggle != null)
					width += twidth;
			} else {
				width = Math.max(0, wHint - marginWidth - marginWidth - thmargin - thmargin);
