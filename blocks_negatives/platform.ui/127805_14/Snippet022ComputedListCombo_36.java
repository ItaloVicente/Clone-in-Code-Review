			protected List calculate() {
				ArrayList result = new ArrayList();
				for (Iterator it = model.iterator(); it.hasNext();) {
					Thing thing = (Thing) it.next();
					if (((Boolean) femaleObservable.getValue()).booleanValue()
							&& !thing.female)
