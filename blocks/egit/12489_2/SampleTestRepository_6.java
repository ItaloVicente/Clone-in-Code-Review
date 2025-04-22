package org.eclipse.egit.ui.wizards.clone;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.SimpleHttpServer;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.Daemon;
import org.eclipse.jgit.transport.DaemonClient;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.util.FileUtils;

public class SampleTestRepository {
	public static final String REPO_NAME = "test";

	public static final String FIX = "fix";

	public static final String v1_0_name = "v1_0";

	public static final String v2_0_name = "v2_0";

	public static final String A_txt_name = "A_txt";

	private static final File trash = new File("target/trash");

	private final TestRepository<FileRepository> src;

	private Daemon d;

	private SimpleHttpServer httpServer;

	private String uri;

	private RevBlob A_txt;

	private RevCommit A, B, C;

	private RevTag v1_0, v2_0;

	private final boolean serveHttp;

	public String getUri() {
		return uri;
	}

	public SampleTestRepository(int n, boolean serveHttp) throws Exception {
		this.serveHttp = serveHttp;
		src = createRepository();
		generateSampleData(n);
		if (serveHttp)
			serveHttp();
		else
			serve();
	}

	private TestRepository<FileRepository> createRepository() throws Exception {
		String gitdirName = "test" + System.currentTimeMillis()
				+ Constants.DOT_GIT;
		File gitdir = new File(trash, gitdirName).getCanonicalFile();
		FileRepository db = new FileRepository(gitdir);
		assertFalse(gitdir.exists());
		db.create();
		return new TestRepository<FileRepository>(db);
	}

	private void generateSampleData(int n) throws Exception {
		A_txt = src.blob("A");
		A = src.commit().add(A_txt_name, A_txt).create();
		src.update(Constants.R_HEADS + Constants.MASTER, A);

		RevCommit X = A;
		for (int i = 0; i < n; i++) {
			X = src.commit().parent(X)
					.add(randomAsciiString(), randomAsciiString()).create();
		}

		B = src.commit().parent(X).add(A_txt_name, "C").add("B", "B").create();
		src.update(Constants.R_HEADS + Constants.MASTER, B);

		v1_0 = src.tag(v1_0_name, B);
		src.update(Constants.R_TAGS + v1_0_name, v1_0);

		C = src.commit().parent(A).add(A_txt_name, "D").add("C", "C").create();
		src.update(Constants.R_HEADS + FIX, C);

		v2_0 = src.tag(v2_0_name, C);
		src.update(Constants.R_TAGS + v2_0_name, v2_0);
	}

	private String randomAsciiString() {
		StringBuilder randstring = new StringBuilder("");
		Random rand = new Random();
		int strlen = rand.nextInt(20) + 10;
		for (int i = 0, j = 0; i < strlen; i++) {
			if (rand.nextInt(2) == 1)
				j = 97;
			else
				j = 65;
			randstring.append((char) (rand.nextInt(26) + j));
		}
		return randstring.toString();
	}

	private void serve() throws IOException {
		d = new Daemon();
		FileResolver<DaemonClient> resolver = new FileResolver<DaemonClient>();
		resolver.exportRepository(REPO_NAME, src.getRepository());
		d.setRepositoryResolver(resolver);
		d.start();
		uri = "git://localhost:" + d.getAddress().getPort() + "/" + REPO_NAME
				+ Constants.DOT_GIT_EXT;
	}

	private void serveHttp() throws Exception{
		httpServer = new SimpleHttpServer(src.getRepository());
		httpServer.start();
		uri = httpServer.getUri().toString();
	}

