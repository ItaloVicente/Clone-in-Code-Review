	protected ICoolBarManager createCoolBarManager2(int style) {
		return createCoolBarManager(style);
	}

	protected Control createToolBarControl(Composite parent) {
		if (toolBarManager != null) {
			if (toolBarManager instanceof IToolBarManager2) {
