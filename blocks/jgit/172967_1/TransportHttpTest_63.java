
	private void assertHeaders(String expected
		HttpConnection fake = Mockito.mock(HttpConnection.class);
		Map<String
		Mockito.doAnswer(invocation -> {
			Object[] args = invocation.getArguments();
			headers.put(args[0].toString()
			return null;
		}).when(fake).setRequestProperty(ArgumentMatchers.anyString()
				ArgumentMatchers.anyString());
		TransportHttp.addHeaders(fake
		Assert.assertEquals(expected
	}

	@Test
	public void testAddHeaders() {
		assertHeaders("{a=b
	}

	@Test
	public void testAddHeaderEmptyValue() {
		assertHeaders("{a-x=b
	}

	@Test
	public void testSkipHeaderWithEmptyKey() {
		assertHeaders("{a=b
		assertHeaders("{a=b
	}

	@Test
	public void testSkipHeaderWithoutKey() {
		assertHeaders("{a=b
	}

	@Test
	public void testSkipHeaderWithInvalidKey() {
		assertHeaders("{a=b
		assertHeaders("{a=b
	}

	@Test
	public void testSkipHeaderWithNonAsciiValue() {
		assertHeaders("{a=b
		assertHeaders("{a=b
	}
