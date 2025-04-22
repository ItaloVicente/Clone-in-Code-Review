		if (definedContextIdsChanged) {
			this.previouslyDefinedContextIds = Util.safeCopy(previouslyDefinedContextIds, String.class);
		} else {
			this.previouslyDefinedContextIds = null;
		}

		if (enabledContextIdsChanged) {
			this.previouslyEnabledContextIds = Util.safeCopy(previouslyEnabledContextIds, String.class);
		} else {
			this.previouslyEnabledContextIds = null;
		}

		this.contextManager = contextManager;
		this.definedContextIdsChanged = definedContextIdsChanged;
		this.enabledContextIdsChanged = enabledContextIdsChanged;
	}

	public IContextManager getContextManager() {
		return contextManager;
	}

	public Set getPreviouslyDefinedContextIds() {
		return previouslyDefinedContextIds;
	}

	public Set getPreviouslyEnabledContextIds() {
		return previouslyEnabledContextIds;
	}

	public boolean haveDefinedContextIdsChanged() {
		return definedContextIdsChanged;
	}

	public boolean haveEnabledContextIdsChanged() {
		return enabledContextIdsChanged;
	}
