							parent.getDisplay().asyncExec(new Runnable() {

								@Override
								public void run() {
									v.setInput(ms);
									((GridData) barContainer.getLayoutData()).exclude = true;
									comp.layout(true);
								}

