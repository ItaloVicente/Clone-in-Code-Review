
			@Override
			public void postWindowOpen(IWorkbenchWindowConfigurer configurer) {
				try {
					ProgressManagerUtil.animateUp(new Rectangle(0, 0, 100, 50));
				}
				catch (NullPointerException e) {
					fail(e.getMessage());
				}
			}

