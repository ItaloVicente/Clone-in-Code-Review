				Runnable obj = new Runnable() {
					@Override
					@Execute
					public void run() {
						action.removePropertyChangeListener(propListener);
					}
				};
