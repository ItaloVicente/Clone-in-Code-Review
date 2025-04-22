				dirFile);

		viewName = myUtil.getPluginLocalizedValue("GitRepositoriesView_name");
		gitCategory = myUtil.getPluginLocalizedValue("GitCategory_name");
	}

	@AfterClass
	public static void afterClass() throws Exception {
		myProject.delete(true, null);
