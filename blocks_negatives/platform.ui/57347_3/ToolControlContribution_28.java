			newComposite.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					ContextInjectionFactory.uninject(tcImpl, parentContext);
					model.setObject(null);
				}
