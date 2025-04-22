						parent.getDisplay().asyncExec(new Runnable() {

							@Override
							public void run() {
								((GridData) barContainer.getLayoutData()).exclude = true;
								comp.layout(true);
							}

