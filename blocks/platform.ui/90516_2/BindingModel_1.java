		for (Binding managerBinding : managerBindings) {
			if (managerBinding.getParameterizedCommand() == null) {
				removalBindings.add(managerBinding);
			} else if (managerBinding.getParameterizedCommand().equals(cmd)) {
				if (managerBinding.getType() == Binding.USER) {
					bindingManager.removeBinding(managerBinding);
				} else if (managerBinding.getType() == Binding.SYSTEM) {
					systemBindings.add(managerBinding);
