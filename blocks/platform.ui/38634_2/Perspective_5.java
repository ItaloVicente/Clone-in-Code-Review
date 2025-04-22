			service.deferUpdates(false);
		}
	}

	public IActionSetDescriptor[] getAlwaysOnActionSets() {
		return alwaysOnActionSets.toArray(new IActionSetDescriptor[alwaysOnActionSets.size()]);
	}

	public IActionSetDescriptor[] getAlwaysOffActionSets() {
		return alwaysOffActionSets.toArray(new IActionSetDescriptor[alwaysOffActionSets.size()]);
	}

