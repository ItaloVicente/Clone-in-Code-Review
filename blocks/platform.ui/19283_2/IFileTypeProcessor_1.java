
package org.eclipse.ui;

public class FileTypeValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	public static class PatternIsEmptyException extends FileTypeValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class EmptyExtensionWithNoNameException extends FileTypeValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class PatternIsSingleWildCharException extends FileTypeValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class IllegalWildCharPositionException extends FileTypeValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class MultipleWildCharsException extends FileTypeValidationException {
		private static final long serialVersionUID = 1L;
	}

}
