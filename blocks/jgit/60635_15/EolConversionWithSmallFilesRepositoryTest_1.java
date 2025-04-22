package org.eclipse.jgit.api;

import org.junit.BeforeClass;

public class EolConversionWithLargeFilesRepositoryTest
		extends AbstractEolConversionRepositoryTest {

	@BeforeClass
	public static void beforeClass() {
		StringBuilder bufCRLF = new StringBuilder();
		StringBuilder bufLF = new StringBuilder();
		for (int i = 0; i < 1024 * 1024; i++) {
			if (i % 17 == 0) {
				bufCRLF.append("\r\n");
				bufLF.append("\n");
			} else {
				bufCRLF.append("A");
				bufLF.append("A");
			}
		}
		CONTENT_CRLF = bufCRLF.toString();
		CONTENT_LF = bufLF.toString();
	}

}
