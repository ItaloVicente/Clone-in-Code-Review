		importProject = new GitProjectsImportPage() {
			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					if (cloneDestination.alreadyClonedInto == null) {
						if (performClone(false))
							cloneDestination.alreadyClonedInto = cloneDestination
									.getDestinationFile().getAbsolutePath();
					}
					setProjectsList(cloneDestination.alreadyClonedInto);
				}
				super.setVisible(visible);
			}
		};
