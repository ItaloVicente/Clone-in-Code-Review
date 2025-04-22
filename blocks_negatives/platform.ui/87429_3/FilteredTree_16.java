			clearButton.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					inactiveImage.dispose();
					activeImage.dispose();
					pressedImage.dispose();
				}
