			@Override
			public void setPattern(String patternString) {
				super.setPattern(patternString);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						enableSelectAllButtons();
					}
				});

			}
