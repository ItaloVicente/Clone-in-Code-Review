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
