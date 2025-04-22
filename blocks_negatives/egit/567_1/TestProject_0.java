	private Path findFileInPlugin(String plugin, String file)
			throws MalformedURLException, IOException {
		IPluginRegistry registry = Platform.getPluginRegistry();
		IPluginDescriptor descriptor = registry.getPluginDescriptor(plugin);
		URL pluginURL = descriptor.getInstallURL();
		URL jarURL = new URL(pluginURL, file);
		URL localJarURL = Platform.asLocalURL(jarURL);
		return new Path(localJarURL.getPath());
