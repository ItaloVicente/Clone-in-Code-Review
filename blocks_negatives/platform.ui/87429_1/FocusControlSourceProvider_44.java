			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					controlToId.remove(e.widget);
					if (currentControl == e.widget) {
						focusIn(null);
