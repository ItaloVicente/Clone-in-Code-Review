		MKeyBinding model = BindingService.createORupdateMKeyBinding(application, table, binding);
		keys.remove(model);
	}

	private void removeBindings() {
		for (MKeyBinding key : keys) {
			if (!key.getTags().contains("type:user")) { //$NON-NLS-1$
				EObject obj = ((EObject) key).eContainer();
				if (obj instanceof MBindingTable) {
					MBindingTable table = (MBindingTable) obj;
					table.getBindings().remove(key);
				}
			}
		}
