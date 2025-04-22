		private Point computeTextLabelSize(int textLabelWidthHint) {
			Point size;
			if (textLabel instanceof Label) {
				GC gc = new GC(textLabel);
				size = FormUtil.computeWrapSize(gc, ((Label) textLabel).getText(), textLabelWidthHint);
				gc.dispose();
			} else
				size = textLabelCache.computeSize(textLabelWidthHint, SWT.DEFAULT);
			return size;
		}

