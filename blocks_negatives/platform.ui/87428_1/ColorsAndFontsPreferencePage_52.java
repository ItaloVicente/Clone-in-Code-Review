        parent.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                if (appliedDialogFont != null)
					appliedDialogFont.dispose();
            }
        });
