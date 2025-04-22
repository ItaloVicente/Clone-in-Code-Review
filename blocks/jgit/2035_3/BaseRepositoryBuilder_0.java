
package org.eclipse.jgit.storage.file;

import java.io.File;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;

public class FileRepositoryBuilderTest extends LocalDiskRepositoryTestCase {
	public void testShouldAutomagicallyDetectGitDirectory() throws Exception {
		FileRepository r = createWorkRepository();
		File d = new File(r.getDirectory()
		d.mkdir();

		assertEquals(r.getDirectory()
				.findGitDir(d).getGitDir());
	}
}
