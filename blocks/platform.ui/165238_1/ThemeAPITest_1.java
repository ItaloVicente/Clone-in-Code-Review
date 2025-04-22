	@Test
	public void testColorRegistryListener_def_swtcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme defaultTheme = getDefaultTheme();

		testColorRegistryPut(defaultTheme, store, SWTCOLOR);
	}

	@Test
	public void testColorRegistryListener_def_rgbcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme defaultTheme = getDefaultTheme();

		testColorRegistryPut(defaultTheme, store, RGBCOLOR);
	}

	@Test
	public void testColorRegistryListener_def_defaultedcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme defaultTheme = getDefaultTheme();

		testColorRegistryPut(defaultTheme, store, DEFAULTEDCOLOR);
	}

	@Test
	public void testColorRegistryListener_def_nooverridecolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme defaultTheme = getDefaultTheme();

		testColorRegistryPut(defaultTheme, store, NOOVERRIDECOLOR);
	}

	@Test
	public void testColorRegistryListener_th1_swtcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme theme1 = getTheme1();

		testColorRegistryPut(theme1, store, SWTCOLOR);
	}

	@Test
	public void testColorRegistryListener_th1_rgbcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme theme1 = getTheme1();

		testColorRegistryPut(theme1, store, RGBCOLOR);
	}

	@Test
	public void testColorRegistryListener_th1_defaultedcolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme theme1 = getTheme1();

		testColorRegistryPut(theme1, store, DEFAULTEDCOLOR);
	}

	@Test
	public void testColorRegistryListener_th1_nooverridecolor() {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		ITheme theme1 = getTheme1();

		testColorRegistryPut(theme1, store, NOOVERRIDECOLOR);
	}

