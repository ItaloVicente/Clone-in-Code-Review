		run("Setting selection to index 30.", new Runnable() { //$NON-NLS-1$
					@Override
					public void run() {
						viewer.setSelection(new StructuredSelection(input
								.get(30)));
					}
				});
