		topLevelModeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				setWorkingSet(workingSet);
				viewer.getFrameList().reset();
			}
