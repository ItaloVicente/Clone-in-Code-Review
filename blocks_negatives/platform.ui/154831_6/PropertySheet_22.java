		pinPropertySheetAction.addPropertyChangeListener(new IPropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (IAction.CHECKED.equals(event.getProperty())) {
					updateContentDescription();
					if (!isPinned()) {
						selectionChanged(currentPart, currentSelection);
					}
