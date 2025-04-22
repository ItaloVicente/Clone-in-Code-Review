            parent.addDisposeListener(new DisposeListener() {
                @Override
				public void widgetDisposed(DisposeEvent e) {
                    toolItem.dispose();
                    toolItem = null;
                }
            });
