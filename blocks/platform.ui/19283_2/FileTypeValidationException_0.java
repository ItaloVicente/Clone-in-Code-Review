
package org.eclipse.ui;

public class FileSuffixValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	public static class DoesNotStartWithWildCharName extends FileSuffixValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class MultipleWildCharsException extends FileSuffixValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class NotEnoughExtensionPartsException extends FileSuffixValidationException {
		private static final long serialVersionUID = 1L;
	}

	public static class IllegalDotPositionException extends FileSuffixValidationException {
		private static final long serialVersionUID = 1L;
	}


}
