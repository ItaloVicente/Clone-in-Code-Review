
package org.eclipse.ui.internal.registry;

import java.util.Iterator;
import java.util.regex.Pattern;
import org.eclipse.ui.FileSuffixValidationException;
import org.eclipse.ui.FileTypeValidationException;
import org.eclipse.ui.IFileTypeProcessor;

public class FileTypeProcessor implements IFileTypeProcessor {

	public static final char STAR_CHAR = '*';
	public static final char DOT_CHAR = '.';
	public static final String STAR = "*"; //$NON-NLS-1$
	public static final String DOT = "."; //$NON-NLS-1$

	public void validateFileTypePattern(String filename) throws FileTypeValidationException {

		if (filename.length() == 0) {
			throw new FileTypeValidationException.PatternIsEmptyException();
		}

		int index = filename.lastIndexOf(DOT_CHAR);
		if (index == filename.length() - 1) {
			if (index == 0 || (index == 1 && filename.charAt(0) == STAR_CHAR)) {
				throw new FileTypeValidationException.EmptyExtensionWithNoNameException();
			}
		}

		index = filename.indexOf(STAR_CHAR);
		if (index > -1) {
			if (filename.length() == 1) {
				throw new FileTypeValidationException.PatternIsSingleWildCharException();
			}
			if (index != 0 || filename.charAt(1) != DOT_CHAR) {
				throw new FileTypeValidationException.IllegalWildCharPositionException();
			}
			if (filename.length() > index && filename.indexOf(STAR_CHAR, index + 1) != -1) {
				throw new FileTypeValidationException.MultipleWildCharsException();
			}
		}
	}

	public boolean isValidFileType(String pattern) {
		try {
			validateFileTypePattern(pattern);
		} catch (FileTypeValidationException e) {
			return false;
		}
		return true;
	}

	public void validateFileSuffixPattern(String pattern) throws FileSuffixValidationException {
		if ((pattern.charAt(0) != STAR_CHAR) || (pattern.charAt(1) != DOT_CHAR))
			throw new FileSuffixValidationException.DoesNotStartWithWildCharName();
		if (pattern.indexOf(STAR_CHAR, 1) != -1)
			throw new FileSuffixValidationException.MultipleWildCharsException();

		String[] parts = pattern.split(Pattern.quote(DOT));
		if (parts.length < 3)
			throw new FileSuffixValidationException.NotEnoughExtensionPartsException();
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].length() == 0)
				throw new FileSuffixValidationException.IllegalDotPositionException();
		}
	}

	public boolean isValidSuffixPattern(String pattern) {
		try {
			validateFileSuffixPattern(pattern);
		} catch (FileSuffixValidationException e) {
			return false;
		}
		return true;
	}

	public String getName(String filename) {
		int index = filename.lastIndexOf(DOT_CHAR);
		if (index == -1) {
			return filename;
		}
		if (index == 0) {
			return STAR;
		}
		return filename.substring(0, index);
	}

	public String getExtension(String filename) {
		int index = filename.lastIndexOf(DOT_CHAR);
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		if (index == filename.length()) {
			return ""; //$NON-NLS-1$
		}
		return filename.substring(index + 1, filename.length());
	}

	public String getPrefix(String filename) {
		int index = filename.indexOf(DOT_CHAR);
		if (index == -1) {
			return filename;
		}
		if (index == 0) {
			return ""; //$NON-NLS-1$
		}
		return filename.substring(0, index - 1);
	}

	public String getSuffix(String filename) {
		int index = filename.indexOf(DOT_CHAR);
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		if (index == filename.length()) {
			return ""; //$NON-NLS-1$
		}
		return filename.substring(index + 1, filename.length());
	}

	public Iterator<String> suffixIterator(final String filename) {
		return new Iterator<String>() {
			final String[] parts = filename.split(Pattern.quote(DOT));
			final StringBuilder sb = new StringBuilder();

			int start = 0;

			public boolean hasNext() {
				return start < parts.length;
			}

			public String next() {
				sb.setLength(0);
				if (start > 0)
					sb.append(STAR_CHAR).append(DOT_CHAR);
				for (int i = start; i < parts.length - 1; i++) {
					sb.append(parts[i]).append(DOT_CHAR);
				}
				sb.append(parts[parts.length - 1]);
				start++;
				return sb.toString();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
