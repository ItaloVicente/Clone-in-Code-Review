            coolItem.addDisposeListener(new DisposeListener() {

                @Override
				public void widgetDisposed(DisposeEvent event) {
                    handleWidgetDispose(event);
                }
            });
