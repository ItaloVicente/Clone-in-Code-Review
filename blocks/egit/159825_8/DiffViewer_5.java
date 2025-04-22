		if (ctx != null) {
			getTextWidget().addFocusListener(new FocusAdapter() {
				IContextActivation context;

				@Override
				public void focusGained(FocusEvent e) {
					context = ctx
							.activateContext(DIFF_VIEWER_CONTEXT_ID);
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
