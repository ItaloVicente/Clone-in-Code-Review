package org.eclipse.jgit.lfs;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;

public class LfsPointer implements Comparable<LfsPointer> {


	public static final int SIZE_THRESHOLD = 200;

	public static final String HASH_FUNCTION_NAME = Constants.LONG_HASH_FUNCTION
			.toLowerCase(Locale.ROOT).replace("-"

	private AnyLongObjectId oid;

	private long size;

	public LfsPointer(AnyLongObjectId oid
		this.oid = oid;
		this.size = size;
	}

	public AnyLongObjectId getOid() {
		return oid;
	}

	public long getSize() {
		return size;
	}

	public void encode(OutputStream out) {
		try (PrintStream ps = new PrintStream(out
				UTF_8.name())) {
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedCharsetException(UTF_8.name());
		}
	}

	@Nullable
	public static LfsPointer parseLfsPointer(InputStream in)
			throws IOException {
		boolean versionLine = false;
		LongObjectId id = null;
		long sz = -1;

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(in
			for (String s = br.readLine(); s != null; s = br.readLine()) {
					continue;
						&& (s.substring(8).trim().equals(VERSION) ||
								s.substring(8).trim().equals(VERSION_LEGACY))) {
					versionLine = true;
					id = LongObjectId.fromString(s.substring(11).trim());
					sz = Long.parseLong(s.substring(5).trim());
				}
			}
			if (versionLine && id != null && sz > -1) {
				return new LfsPointer(id
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "LfsPointer: oid=" + oid.name() + "
				+ size;
	}

	@Override
	public int compareTo(LfsPointer o) {
		int x = getOid().compareTo(o.getOid());
		if (x != 0) {
			return x;
		}

		return (int) (getSize() - o.getSize());
	}
}

