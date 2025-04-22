package org.eclipse.jgit.api;

import org.junit.BeforeClass;

public class EolConversionWithSmallFilesRepositoryTest extends AbstractEolConversionRepositoryTest {

	@BeforeClass
	public static void beforeClass() {
		CONTENT_CRLF = "a\r\nb";
		CONTENT_LF = "a\nb";
	}

}
