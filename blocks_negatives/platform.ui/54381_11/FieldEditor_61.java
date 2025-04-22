            label.addDisposeListener(new DisposeListener() {
                @Override
				public void widgetDisposed(DisposeEvent event) {
                    label = null;
                }
            });
