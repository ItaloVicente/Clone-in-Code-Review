			control.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(toolbarModel);
					Object dispose = transientData.get(POST_PROCESSING_DISPOSE);
					if (dispose instanceof Runnable) {
						((Runnable) dispose).run();
					}
					transientData.remove(POST_PROCESSING_DISPOSE);
					transientData.remove(DISPOSE_ADDED);
