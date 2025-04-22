        styledText.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                handCursor.dispose();
                handCursor = null;
                busyCursor.dispose();
                busyCursor = null;
            }
        });
