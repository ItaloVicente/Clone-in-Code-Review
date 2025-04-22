
package org.eclipse.jgit.lfs.lib;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.jgit.lfs.LfsPointer;
import org.junit.Test;

public class LFSPointerTest {
	@Test
	public void testEncoding() throws IOException {
		final String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer ptr = new LfsPointer(id
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ptr.encode(baos);
		baos.close();
				+ s + "\nsize 4\n"
				baos.toString(StandardCharsets.UTF_8.name()));
	}
}
