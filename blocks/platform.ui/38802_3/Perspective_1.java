
			temp = new ArrayList<IActionSetDescriptor>();
			createInitialActionSets(temp, alwaysOff);
			for (IActionSetDescriptor descriptor : temp) {
				if (!alwaysOffActionSets.contains(descriptor)) {
					alwaysOffActionSets.add(descriptor);
				}
			}
