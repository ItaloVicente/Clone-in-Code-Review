			animationShell.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (engine != null)
						engine.cancelAnimation();
				}
