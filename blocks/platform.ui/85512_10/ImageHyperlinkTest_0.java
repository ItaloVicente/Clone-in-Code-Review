	public void testPaintHyperlinkDoesNotLeakDisabledImage() throws Exception {
		Image prevImage = createGradient();
		imageHyperlink.setImage(prevImage);
		imageHyperlink.setEnabled(false);
		imageHyperlink.paintHyperlink(gc);

		Image prevDisabledImage = getDisabledImage(imageHyperlink);
		imageHyperlink.paintHyperlink(gc);

		assertSame(prevDisabledImage, getDisabledImage(imageHyperlink));
	}
