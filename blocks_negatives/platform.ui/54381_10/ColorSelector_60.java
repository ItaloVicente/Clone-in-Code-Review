        fButton.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent event) {
                if (fImage != null) {
                    fImage.dispose();
                    fImage = null;
                }
                if (fColor != null) {
                    fColor.dispose();
                    fColor = null;
                }
            }
        });
