			composite.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					setMessage(IDEWorkbenchMessages.UnsupportedVM_message,
							IMessageProvider.WARNING);
				}
			});
