        browser.addStatusTextListener(new StatusTextListener() {
            @Override
			public void changed(StatusTextEvent event) {
                if (container != null) {
                    IStatusLineManager status = container.getActionBars()
                            .getStatusLineManager();
                    status.setMessage(event.text);
                }
            }
        });
