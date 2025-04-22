		}
		ActivationListener activationListener = new ActivationListener();
		control.addListener(SWT.Deactivate, activationListener);
		control.addListener(SWT.FocusOut, activationListener);
		control.addListener(SWT.Activate, activationListener);
		control.addListener(SWT.FocusIn, activationListener);

