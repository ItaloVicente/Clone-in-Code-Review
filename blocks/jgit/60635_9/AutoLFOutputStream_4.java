
package org.eclipse.jgit.util.io;

import java.io.InputStream;

@SuppressWarnings("deprecation")
public class AutoLFInputStream extends EolCanonicalizingInputStream {

	public AutoLFInputStream(InputStream in
		super(in
	}

	public AutoLFInputStream(InputStream in
			boolean abortIfBinary) {
		super(in
	}

}
