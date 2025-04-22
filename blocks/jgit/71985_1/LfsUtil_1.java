package org.eclipse.jgit.lfs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.jgit.lfs.lib.LongObjectId;

public class LfsPointer {


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

	public static LfsParserResult parseLfsPointer(InputStream in
			throws IOException {
		boolean v = false;
		LongObjectId id = null;
		long si = -1;
		LfsParserResult ret = new LfsParserResult();
		if ((ret.bufferSize = in.read(ret.buffer
				ret.buffer.length)) == -1) {
			return null;
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(ret.buffer
			for (String s = br.readLine(); s != null; s = br.readLine()) {
					continue;
						&& s.substring(8).trim().equals(VERSION)) {
					v = true;
					id = LongObjectId.fromString(s.substring(4).trim());
					si = Long.parseLong(s.substring(5).trim());
				} else {
					return null;
				}
			}
			if (v && id != null && si > -1) {
				ret.pointer = new LfsPointer(id
			}
		}
		return ret;
	}
}

class LfsParserResult {
	public byte[] buffer = new byte[1024];

	public int bufferSize = -1;

	public LfsPointer pointer = null;

}
