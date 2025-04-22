            text.addDisposeListener(new DisposeListener() {
                @Override
				public void widgetDisposed(DisposeEvent e) {
                    if (font != null) {
						font.dispose();
					}
                }
            });
