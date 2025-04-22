
			for (IActionSetDescriptor descriptor : createInitialActionSets(alwaysOff)) {
				if (!alwaysOffActionSets.contains(descriptor)) {
					alwaysOffActionSets.add(descriptor);
				}
			}
