
package org.eclipse.jgit.subtree;

import java.io.IOException;

@SuppressWarnings("serial")
public class SubtreeFooterException extends IOException {

	SubtreeFooterException(String msg) {
		super(msg);
	}
}
