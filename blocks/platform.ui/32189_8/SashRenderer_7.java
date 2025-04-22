		final PartSizeInfo partSizeInfo = new PartSizeInfo(element);
		element.getTransientData().put(PartSizeInfo.KEY_TRANSIENT_DATA,
				partSizeInfo);

		if (element.getWidget() instanceof Control) {
			((Control) element.getWidget())
					.addDisposeListener(new DisposeListener() {

						@Override
						public void widgetDisposed(DisposeEvent e) {
							partSizeInfo.storeInfo(element);
						}
					});
