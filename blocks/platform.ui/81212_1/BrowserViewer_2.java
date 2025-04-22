		browser.addStatusTextListener(event -> {
			if (container != null) {
				IStatusLineManager status = container.getActionBars().getStatusLineManager();
				status.setMessage(event.text);
			}
		});
