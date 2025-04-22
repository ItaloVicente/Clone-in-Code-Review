package org.eclipse.jgit.mailmap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;

public class MailmapParserTest {

	@Test
	public void simpleNameAndEmail() throws Exception {
		Mailmap mailmap = parse("mailmap-simple");
		PersonIdent mapped = mailmap
				.map(new PersonIdent("Commit Name"
		assertThat(mapped.getName()
		assertThat(mapped.getEmailAddress()
	}

	@Test
	public void complexEmailOnly() throws Exception {
		Mailmap mailmap = parse("mailmap-complex-email-only");
		PersonIdent mapped = mailmap
				.map(new PersonIdent("Commit Name"
		assertThat(mapped.getName()
		assertThat(mapped.getEmailAddress()
	}

	@Test
	public void complexNameAndEmail() throws Exception {
		Mailmap mailmap = parse("mailmap-complex-proper-and-email");
		PersonIdent mapped = mailmap
				.map(new PersonIdent("Commit Name"
		assertThat(mapped.getName()
		assertThat(mapped.getEmailAddress()
	}

	@Test
	public void complexNameAllSpecified() throws Exception {
		Mailmap mailmap = parse("mailmap-complex-all");
		PersonIdent mapped = mailmap
				.map(new PersonIdent("Commit Name"
		assertThat(mapped.getName()
		assertThat(mapped.getEmailAddress()
	}

	private Mailmap parse(String mailmapFilename) throws Exception {
		File testResourceFile = JGitTestUtil
				.getTestResourceFile(mailmapFilename);
		try (FileInputStream fis = new FileInputStream(testResourceFile)) {
			return MailmapParser.parse(fis);
		}
	}
}
