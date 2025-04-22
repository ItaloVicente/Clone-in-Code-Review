		if (Display.getCurrent() == null) {
			StyledText text = commentViewer.getTextWidget();
			if (!text.isDisposed()) {
				text.getDisplay().asyncExec(() -> {
					if (text.isDisposed()) {
						return;
					}
					internalSetWrap(wrap);
				});
			}
		} else {
			internalSetWrap(wrap);
		}
	}

	private void internalSetWrap(boolean wrap) {
