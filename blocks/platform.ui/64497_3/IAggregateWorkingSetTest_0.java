			final boolean[] propertyFiredSuccessfully = new boolean[] { false };

			manager.addPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					try {
						IWorkingSet workingSetB = manager.getWorkingSet(nameB);
						IAdaptable[] elements = workingSetB.getElements();

						if (elements.length != wSetB.getElements().length) {
							fail("Working set B was not fully restored at the time the event was fired");
						}

						synchronized (propertyFiredSuccessfully) {
							propertyFiredSuccessfully[0] = true;
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					} finally {
						manager.removePropertyChangeListener(this);
					}
				}
			});
