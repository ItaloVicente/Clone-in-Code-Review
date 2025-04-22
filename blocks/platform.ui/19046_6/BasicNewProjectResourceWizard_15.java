		if (this.defaultProjectName == null && this.defaultLocation != null) {
			this.defaultProjectName = this.defaultLocation.getName();
		}
		this.mainPage.setInitialProjectName(this.defaultProjectName);
		this.mainPage.setInitialLocation(this.defaultLocation);
		this.mainPage.setLockLocation(this.lockLocation);
