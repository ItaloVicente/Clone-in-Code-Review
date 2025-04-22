			Display display = resourceNames.getDisplay();
			final int itemIndex[] = { 0 };
			final int itemCount[] = { 0 };
			final boolean[] disposed = { false };
			display.syncExec(() -> {
				if (resourceNames.isDisposed()) {
					disposed[0] = true;
					return;
				}
				itemCount[0] = resourceNames.getItemCount();
