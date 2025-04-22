	@Test
	public void testMultipleSectionGradientInstances() throws Exception {
		Display display = Display.getCurrent();
		Image gradient = FormImages.getInstance().getSectionGradientImage(new Color(display, 200, 200, 200),
				new Color(display, 0, 0, 0), 30, 16, 3, display);
		int count;
		for (count = 1; count < 20; count++)
			Assert.assertEquals("getSectionGradientImage(...) returned a different image for the same params on iteration " + count,
					gradient, FormImages.getInstance().getSectionGradientImage(new Color(display, 200, 200, 200),
							new Color(display, 0, 0, 0), 30, 16, 3, display));
		for (; count > 0; count--) {
			FormImages.getInstance().markFinished(gradient, display);
			if (count != 1)
				Assert.assertFalse("markFinished(...) disposed a shared image early on iteration " + count,
						gradient.isDisposed());
			else
				Assert.assertTrue("markFinished(...) did not dispose a shared image on the last call",
						gradient.isDisposed());
		}
		Assert.assertNull("descriptors map", getDescriptors(FormImages.getInstance()));
	}

