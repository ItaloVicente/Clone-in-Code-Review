			getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					PushResultDialog dlg = new PushResultDialog(shell,
							repository, result[0], op.getDestinationString(),
							false);
					dlg.showConfigureButton(false);
					dlg.open();
				}
			});
