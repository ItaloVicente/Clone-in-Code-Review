			children[i].addPropertyListener( new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int propId) {
					handlePropertyChange(propId);
				}
			});
