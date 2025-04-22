package org.eclipse.ui;

import java.util.Iterator;

public interface IFileTypeProcessor {
	public void validateFileTypePattern(String pattern) throws FileTypeValidationException;

	public boolean isValidFileType(String pattern);

	public void validateFileSuffixPattern(String pattern) throws FileSuffixValidationException;

	public boolean isValidSuffixPattern(String pattern);

	public String getName(String filename);

	public String getExtension(String filename);
	
	public String getPrefix(String filename);

	public String getSuffix(String filename);

	public Iterator<String> suffixIterator(final String filename);
}
