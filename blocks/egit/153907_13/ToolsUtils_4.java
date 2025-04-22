		AtomicInteger result = new AtomicInteger();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				MessageBox mbox = new MessageBox(
						Display.getDefault().getActiveShell(),
						SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mbox.setText(textHeader);
				mbox.setMessage(message);
				result.set(mbox.open());
			}
		};
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
		} else {
			runnable.run();
		}
		return result.get();
