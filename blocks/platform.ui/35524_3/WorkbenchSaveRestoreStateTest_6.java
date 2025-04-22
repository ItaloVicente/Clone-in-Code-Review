				super.initialize(c);
				c.setSaveAndRestore(true);
			}

			public void eventLoopIdle(Display d) {
				workbenchConfig.getWorkbench().restart();
			}
