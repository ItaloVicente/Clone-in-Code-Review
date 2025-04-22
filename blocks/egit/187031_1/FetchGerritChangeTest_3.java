package org.eclipse.egit.ui.internal.clone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GitUrlCheckerTest {

	@Test
	public void testUrl() {
		assertEquals("https://some.example.org/things-and-stuff/my_repo.git",
				GitUrlChecker.sanitizeAsGitUrl(
						"  https://some.example.org/things-and-stuff/my_repo.git "));
	}

	@Test
	public void testFileUrlWithSpace() {
		assertEquals("file:///C:/User/Archibald Thor/git/repo.git",
				GitUrlChecker.sanitizeAsGitUrl(
						"file:///C:/User/Archibald Thor/git/repo.git"));
	}

	@Test
	public void testGitClonePrefix() {
		assertEquals("https://git.eclipse.org/r/egit/egit",
				GitUrlChecker.sanitizeAsGitUrl(
						"  git clone https://git.eclipse.org/r/egit/egit  "));
	}

	@Test
	public void testGitClonePrefixWithQuotes() {
		assertEquals("https://git.eclipse.org/r/egit/egit",
				GitUrlChecker.sanitizeAsGitUrl(
						"  git clone \"https://git.eclipse.org/r/egit/egit\"  "));
	}

	@Test
	public void testBrokenUrlWithSpace() {
		assertEquals("https://git.eclipse.org/r/e", GitUrlChecker
				.sanitizeAsGitUrl("https://git.eclipse.org/r/e git/egit"));
	}

	@Test
	public void testMultiline() {
		assertEquals("https://git.eclipse.org/r/egit/egit",
				GitUrlChecker.sanitizeAsGitUrl(
						"  git clone \"https://git.eclipse.org/r/egit/egit\"  \n"
								+ "https://some.example.org/a/b.git"));
	}

}
