			final PushOperationResult result = op.execute(monitor);
			getShell().getDisplay().asyncExec(() -> {
				PushResultDialog dlg = new PushResultDialog(getShell(),
						getRepository(), result, op.getDestinationString(),
						true, PushMode.UPSTREAM);
				dlg.showConfigureButton(false);
				dlg.open();
			});
		} catch (CoreException e) {
			Activator.handleError(e.getMessage(), e, true);
