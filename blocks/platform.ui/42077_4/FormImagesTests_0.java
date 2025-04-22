		Assert.assertNull("descriptors map", getDescriptors(FormImages.getInstance()));
	}

	private static HashMap getDescriptors(FormImages formImages) throws Exception {
		Field field = formImages.getClass().getDeclaredField("descriptors");
		field.setAccessible(true);
		return (HashMap) field.get(formImages);
