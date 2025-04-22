		IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
		doReturn(new FontData[] { fontData }).when(provider).getFont(symbolicName);

		Hashtable<String, Object> properties = new Hashtable<>();
		properties.put("service.ranking", "1000");

		FrameworkUtil.getBundle(getClass()).getBundleContext().registerService(IColorAndFontProvider.class, provider,
				null);
