
package org.eclipse.jgit.lfs.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.lfs.internal.LfsText;

public class LfsUnauthorized extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsUnauthorized(String operation
		super(MessageFormat.format(LfsText.get().lfsUnathorized
				name));
	}
}
