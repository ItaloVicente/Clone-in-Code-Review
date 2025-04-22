
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.storage.pack.PackOutputStream;

abstract class ByteWindow {
	protected final PackFile pack;

	protected final long start;

	protected final long end;

	protected ByteWindow(PackFile p
		pack = p;
		start = s;
		end = start + n;
	}

	final int size() {
		return (int) (end - start);
	}

	final boolean contains(PackFile neededFile
		return pack == neededFile && start <= neededPos && neededPos < end;
	}

	final int copy(long pos
		return copy((int) (pos - start)
	}

	protected abstract int copy(int pos

	abstract void write(PackOutputStream out
			throws IOException;

	final int setInput(long pos
		return setInput((int) (pos - start)
	}

	protected abstract int setInput(int pos
			throws DataFormatException;
}
