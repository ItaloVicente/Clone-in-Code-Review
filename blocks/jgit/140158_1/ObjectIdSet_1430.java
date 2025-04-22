
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.util.IO;

public class ObjectIdSerializer {
	private static final byte NULL_MARKER = 0;

	private static final byte NON_NULL_MARKER = 1;

	public static void write(OutputStream out
			throws IOException {
		if (id != null) {
			out.write(NON_NULL_MARKER);
			writeWithoutMarker(out
		} else {
			out.write(NULL_MARKER);
		}
	}

	public static void writeWithoutMarker(OutputStream out
			throws IOException {
		id.copyRawTo(out);
	}

	@Nullable
	public static ObjectId read(InputStream in) throws IOException {
		byte marker = (byte) in.read();
		switch (marker) {
		case NULL_MARKER:
			return null;
		case NON_NULL_MARKER:
			return readWithoutMarker(in);
		default:
		}
	}

	@NonNull
	public static ObjectId readWithoutMarker(InputStream in) throws IOException {
		final byte[] b = new byte[OBJECT_ID_LENGTH];
		IO.readFully(in
		return ObjectId.fromRaw(b);
	}

	private ObjectIdSerializer() {
	}
}
