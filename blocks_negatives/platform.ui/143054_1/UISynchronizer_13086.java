    public UISynchronizer(Display display, UILockListener lock) {
        super(display);
        this.lockListener = lock;
        use32Threading = WorkbenchPlugin.getDefault()
				.getPreferenceStore().getBoolean(
						IPreferenceConstants.USE_32_THREADING);
    }
