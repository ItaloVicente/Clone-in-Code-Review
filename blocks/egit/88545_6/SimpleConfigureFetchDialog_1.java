			final FetchResult result = op.execute(monitor);
			getShell().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					FetchResultDialog dlg;
					dlg = new FetchResultDialog(getShell(), getRepository(),
							result, op.getSourceString());
					dlg.showConfigureButton(false);
					dlg.open();
