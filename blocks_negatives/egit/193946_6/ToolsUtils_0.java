				MessageBox mbox = new MessageBox(
						Display.getDefault().getActiveShell(),
						SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mbox.setText(textHeader);
				mbox.setMessage(message);
				result.set(mbox.open());
