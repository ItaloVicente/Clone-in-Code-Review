
package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;

public class UnsafeCRLFConversionException extends IOException {

	private static final long serialVersionUID = 1L;

	public UnsafeCRLFConversionException() {
		this(JGitText.get().unsafeCrlfConversion);
	}

	public UnsafeCRLFConversionException(String message) {
		super(message);
	}
}
