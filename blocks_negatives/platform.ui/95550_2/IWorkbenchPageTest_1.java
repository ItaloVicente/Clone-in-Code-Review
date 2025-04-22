		IPropertyChangeListener listener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				IWorkingSet[] oldSets = (IWorkingSet[]) event.getOldValue();
				assertTrue(Arrays.equals(sets[0], oldSets));
				sets[0] = (IWorkingSet[]) event.getNewValue();
			}
