
	}

	public GerritConfigurationPage(boolean createGerritCheckbox) {
		super(GerritConfigurationPage.class.getName());
		this.createGerritCheckbox  = createGerritCheckbox;
		setTitle(UIText.GerritConfigurationPage_title);
		setDescription(UIText.GerritConfigurationPage_PageDescription);
