		this.rulerInfo = rulerInfo;
		create();
	}

	public BlameInformationControl(Shell parentShell,
			BlameInformationControl hoverInformationControl) {
		super(parentShell, new ToolBarManager());
		this.hoverInformationControl = hoverInformationControl;
