	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		registerImage(registry, IMG_FORM_BG, "form_banner.gif");
		registerImage(registry, IMG_LARGE, "large_image.gif");
		registerImage(registry, IMG_HORIZONTAL, "th_horizontal.gif");
		registerImage(registry, IMG_VERTICAL, "th_vertical.gif");
		registerImage(registry, IMG_SAMPLE, "sample.png");
		registerImage(registry, IMG_WIZBAN, "newprj_wiz.png");
		registerImage(registry, IMG_LINKTO_HELP, "linkto_help.gif");
		registerImage(registry, IMG_HELP_TOPIC, "topic.gif");
		registerImage(registry, IMG_HELP_CONTAINER, "container_obj.gif");
		registerImage(registry, IMG_HELP_TOC_CLOSED, "toc_closed.gif");
		registerImage(registry, IMG_HELP_TOC_OPEN, "toc_open.gif");
		registerImage(registry, IMG_CLOSE, "close_view.gif");
		registerImage(registry, IMG_HELP_SEARCH, "e_search_menu.gif");
		registerImage(registry, IMG_CLEAR, "clear.gif");
		registerImage(registry, IMG_NW, "nw.gif");
	}

	private void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path("icons/" + fileName);
			URL url = FileLocator.find(getBundle(), path, null);
			if (url!=null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}

