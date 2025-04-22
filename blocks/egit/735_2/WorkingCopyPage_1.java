package org.eclipse.egit.ui.wizards.clone;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.transport.Daemon;

public class SampleTestRepository {
	public static final String REPO_NAME = "test";

	public static final String FIX = "fix";

	public static final String v1_0_name = "v1_0";

	public static final String v2_0_name = "v2_0";

	public static final String A_txt_name = "A_txt";

	private static final File trash = new File("trash");

	private final TestRepository src;

	private Daemon d;

	private String uri;

	private RevBlob A_txt;

	private RevCommit A, B, C;

	private RevTag v1_0, v2_0;

	public String getUri() {
		return uri;
	}

	public SampleTestRepository() throws Exception {
		src = createRepository();
		generateSampleData();
		serve();
	}

	private TestRepository createRepository() throws Exception {
		String gitdirName = "test" + System.currentTimeMillis()
				+ Constants.DOT_GIT;
		File gitdir = new File(trash, gitdirName).getCanonicalFile();
		Repository db = new Repository(gitdir);
		assertFalse(gitdir.exists());
		db.create();
		return new TestRepository(db);
	}

	private void generateSampleData() throws Exception {
		A_txt = src.blob("A");
		A = src.commit().add(A_txt_name, A_txt).create();
		src.update(Constants.R_HEADS + Constants.MASTER, A);

		B = src.commit().parent(A).add(A_txt_name, "C").add("B", "B").create();
		src.update(Constants.R_HEADS + Constants.MASTER, B);

		v1_0 = src.tag(v1_0_name, B);
		src.update(Constants.R_TAGS + v1_0_name, v1_0);

		C = src.commit().parent(A).add(A_txt_name, "D").add("C", "C").create();
		src.update(Constants.R_HEADS + FIX, C);

		v2_0 = src.tag(v2_0_name, C);
		src.update(Constants.R_TAGS + v2_0_name, v2_0);
	}

	private void serve() throws IOException {
		d = new Daemon();
		d.exportRepository(REPO_NAME, src.getRepository());
		d.start();
		uri = "git://localhost:" + d.getAddress().getPort() + "/" + REPO_NAME
				+ Constants.DOT_GIT;
		System.out.println("test daemon listens on " + uri);
	}

	public void shutDown() throws FileNotFoundException {
		d.stop();
		if (!System.getProperties().contains("test-repo-no-cleanup"))
			delete(trash);
	}

	private void delete(File f) throws FileNotFoundException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

}
