	private void assertArrayEquals(Object[] array1, Object[] array2) {
		for (int i = 0; i < array1.length; i++) {

			Assert.assertEquals(array1[i], array2[i]);
		}
	}

