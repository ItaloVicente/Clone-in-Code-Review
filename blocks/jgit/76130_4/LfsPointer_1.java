package org.eclipse.jgit.lfs;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;

public class LfsPointer {

	public static final String HASH_FUNCTION_NAME = Constants.LONG_HASH_FUNCTION
			.toLowerCase().replace("-"

	private LongObjectId oid;

	private long size;

	public LfsPointer(LongObjectId oid
		this.oid = oid;
		this.size = size;
	}

	public LongObjectId getOid() {
		return oid;
	}

	public long getSize() {
		return size;
	}

	public void encode(OutputStream out) {
		try (PrintStream ps = new PrintStream(out)) {
			ps.println(VERSION);
			ps.println(LongObjectId.toString(oid));
			ps.println(size);
		}
	}
}
