		Assert.isTrue(element instanceof IResource);
		if (descriptor == null) {
			URL source = TestPlugin.getDefault().getDescriptor().getInstallURL();
			try {
				descriptor = ImageDescriptor.createFromURL(new URL(source, "icons/binary_co.gif"));
			} catch (MalformedURLException exception) {
				return null;
			}
		}
		return descriptor;
