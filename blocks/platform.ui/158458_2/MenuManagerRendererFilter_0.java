				ParameterizedCommand cmd = ((MHandledMenuItem) element).getWbCommand();
				if (cmd != null) {
					updateCommandEnablement(evalContext, element, cmd);
				}
			} else if (updateEnablement && OpaqueElementUtil.isOpaqueMenuItem(element)) {
				Object obj = OpaqueElementUtil.getOpaqueItem(element);
				ParameterizedCommand cmd = getCommand(obj);
				if (cmd != null) {
					updateCommandEnablement(evalContext, element, cmd);
