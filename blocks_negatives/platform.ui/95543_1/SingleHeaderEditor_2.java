		headerForm.getForm().getDisplay().timerExec(5000, new Runnable() {
			@Override
			public void run() {
				sform.setText("<Another text>");
			}
		});
