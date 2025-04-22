		IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
		doReturn(rgb).when(provider).getColor(symbolicName);
		Hashtable<String, Object> properties = new Hashtable<>();
		properties.put("service.ranking", "1000");

		FrameworkUtil.getBundle(getClass()).getBundleContext().registerService(IColorAndFontProvider.class,
							provider, null);
