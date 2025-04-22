
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.internal.JGitText;

public final class DisabledOutputStream extends OutputStream {
	public static final DisabledOutputStream INSTANCE = new DisabledOutputStream();

	private DisabledOutputStream() {
	}

	@Override
	public void write(int b) throws IOException {
		throw new IllegalStateException(JGitText.get().writingNotPermitted);
	}
}
