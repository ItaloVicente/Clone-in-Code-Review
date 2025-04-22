            radioBox.addDisposeListener(new DisposeListener() {
                @Override
				public void widgetDisposed(DisposeEvent event) {
                    radioBox = null;
                    radioButtons = null;
                }
            });
