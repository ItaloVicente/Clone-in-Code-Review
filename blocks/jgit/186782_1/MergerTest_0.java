	@Before
	public void enableRename() throws IOException
		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
		config.save();
	}

