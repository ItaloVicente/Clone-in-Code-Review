        messageLabel.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent event) {
                JFaceResources.getFontRegistry().removeListener(fontListener);
            }
        });
