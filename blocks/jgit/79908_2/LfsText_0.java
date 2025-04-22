
package org.eclipse.jgit.lfs.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.lfs.internal.LfsText;

public class LfsUnavailable extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsUnavailable(String name) {
		super(MessageFormat.format(LfsText.get().lfsUnavailable
	}
}
