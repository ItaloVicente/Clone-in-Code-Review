		if (ctx != null) {
			getTextWidget().addFocusListener(new FocusAdapter() {
				IContextActivation context;

				@Override
				public void focusGained(FocusEvent e) {
					context = ctx
							.activateContext("org.eclipse.egit.ui.DiffViewer"); //$NON-NLS-1$
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (context != null) {
						ctx.deactivateContext(context);
						context = null;
					}
				}
			});
		}
