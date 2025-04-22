	protected static InputStream createInputStream(String[] inputLines) {
		return createInputStream(Arrays.asList(inputLines));
	}

	protected static InputStream createInputStream(List<String> inputLines) {
		String input = String.join(System.lineSeparator()
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		return inputStream;
	}

