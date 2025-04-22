			Point tsize = NULL_SIZE;
			Point tcsize = NULL_SIZE;
			if (toggle != null)
				tsize = toggleCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int twidth = clientArea.width - marginWidth - marginWidth
					- thmargin - thmargin;
			if (tsize.x > 0)
				twidth -= tsize.x + IGAP;
			if (textClient != null) {
				tcsize = textClientCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			Point size = NULL_SIZE;
			if (textLabel != null) {
				if (tcsize.x > 0 && FormUtil.isWrapControl(textClient)) {
					size = textLabelCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					if (twidth < size.x + IGAP + tcsize.x) {
						twidth -= IGAP;
						if (textLabel instanceof Label) {
							GC gc = new GC(textLabel);
							size = FormUtil.computeWrapSize(gc, ((Label)textLabel).getText(), Math.round(twidth*(size.x/(float)(size.x+tcsize.x))));
							gc.dispose();
						} else
							size = textLabelCache.computeSize(Math.round(twidth*(size.x/(float)(size.x+tcsize.x))), SWT.DEFAULT);
						tcsize = textClientCache.computeSize(twidth-size.x, SWT.DEFAULT);
					}
				}
				else {
					if (tcsize.x > 0)
						twidth -= tcsize.x + IGAP;
					size = textLabelCache.computeSize(twidth, SWT.DEFAULT);
