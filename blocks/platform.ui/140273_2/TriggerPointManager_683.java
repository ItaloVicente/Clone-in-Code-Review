		for (Object object : objects) {
			if (object instanceof RegistryTriggerPoint) {
				triggerMap.remove(((RegistryTriggerPoint) object).getId());
			}
		}
	}
