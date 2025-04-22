		for (int i = 0; i < managerBindings.length; i++) {
			if (managerBindings[i].getParameterizedCommand() == null) {
				removalBindings.add(managerBindings[i]);
			} else if (managerBindings[i].getParameterizedCommand().equals(cmd)) {
				if (managerBindings[i].getType() == Binding.USER) {
					bindingManager.removeBinding(managerBindings[i]);
				} else if (managerBindings[i].getType() == Binding.SYSTEM) {
					systemBindings.add(managerBindings[i]);
