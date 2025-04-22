	public void testLabelProviderTextImageAdapter() {
		Image fgImage = ImageDescriptor.createFromFile(TestLabelProvider.class, "images/java.gif").createImage();
		LabelProvider provider = LabelProvider.createTextImageProvider(e -> providedString((TestElement) e), e -> fgImage);
		fViewer.setLabelProvider(provider);
		TestElement first = fRootElement.getFirstChild();
		LabelProvider retrievedProvider = (LabelProvider) fViewer.getLabelProvider();
		String newLabel = providedString(first);
		assertEquals("rendered label", newLabel, getItemText(0));
		assertEquals("same image", retrievedProvider.getImage(first), fgImage);
	}

	public void testLabelProviderTextAdapter() {
		LabelProvider provider = LabelProvider.createTextProvider(e -> providedString((TestElement) e));
		fViewer.setLabelProvider(provider);
		TestElement first = fRootElement.getFirstChild();
		String newLabel = providedString(first);
		assertEquals("rendered label", newLabel, getItemText(0));
	}

	public void testLabelProviderImageAdapter() {
		Image fgImage = ImageDescriptor.createFromFile(TestLabelProvider.class, "images/java.gif").createImage();
		LabelProvider provider = LabelProvider.createImageProvider(e -> fgImage);
		fViewer.setLabelProvider(provider);
		TestElement first = fRootElement.getFirstChild();
		LabelProvider retrievedProvider = (LabelProvider) fViewer.getLabelProvider();
		assertEquals("same image", retrievedProvider.getImage(first), fgImage);
	}

