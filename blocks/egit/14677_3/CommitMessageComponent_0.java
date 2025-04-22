		authorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!listenersEnabled)
					return;
				listener.statusUpdated();
			}
		});
