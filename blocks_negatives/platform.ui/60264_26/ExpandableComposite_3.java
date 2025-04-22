			if (textLabel != null) {
				if (tcsize.x > 0 && FormUtil.isWrapControl(textClient)) {
					size = textLabelCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					if (innertHint != SWT.DEFAULT && innertHint < size.x + IGAP + tcsize.x) {
						innertHint -= IGAP;
						if (textLabel instanceof Label) {
							GC gc = new GC(textLabel);
							size = FormUtil.computeWrapSize(gc, ((Label)textLabel).getText(), Math.round(innertHint*(size.x/(float)(size.x+tcsize.x))));
							gc.dispose();
						} else
							size = textLabelCache.computeSize(Math.round(innertHint*(size.x/(float)(size.x+tcsize.x))), SWT.DEFAULT);
						tcsize = textClientCache.computeSize(innertHint-size.x, SWT.DEFAULT);
					}
				} else {
					if (innertHint != SWT.DEFAULT && tcsize.x > 0)
						innertHint -= IGAP + tcsize.x;
					size = textLabelCache.computeSize(innertHint, SWT.DEFAULT);
				}
