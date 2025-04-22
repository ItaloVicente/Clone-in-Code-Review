package org.eclipse.egit.ui.internal.sharing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.swtbot.eclipse.finder.SWTBotEclipseTestCase;
import net.sf.swtbot.wait.Conditions;
import net.sf.swtbot.widgets.SWTBotShell;
import net.sf.swtbot.widgets.SWTBotTree;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jgit.lib.Repository;

/**
 * UI Tests for the Git import wizard.
 * <p>
 * Only UI aspects are interesting.
 * <p>
 * TODO:
 * <ul>
 * <li>Failure at fetch step
 * <li>Failure during fetch step
 * <li>Failure post fetch step (bad repo?)
 * </ul>
 */
public class GitCloneWizardTest extends SWTBotEclipseTestCase {
	/**
	 * Test that defaults and derived values in dialogs are set properly and
	 * that cloning starts and stops properly for a normal run. It also samples
	 * the cloned repository to see that a clone and checkout has occurred.
	 * <p>
	 * The clone is backgrounded so no import to the workspace, nor any sharing
	 * occurs.
	 *
	 * @throws Exception
	 */
	public void testClone() throws Exception {
		goToImportGitRepository();
		assertSourceParams(null, "repo.or.cz", "/egit.git", "git", "", true, "",
				"", false, false);
		bot.button("Next >").click();

		assertEquals("historical/pre-eclipse", bot.table().getTableItem(0).getText());
		assertTrue(bot.table().getTableItem(0).isChecked());
		bot.button("Next >").click();

		File destRepo = new File(new File(ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().getParent(),
				"junit-workspace"), "egit");
		assertEquals(destRepo.toString(), bot.textWithLabel("Directory:")
				.getText());
		assertEquals("master", bot.comboBoxWithLabel("Initial branch:")
				.getText());
		assertEquals("origin", bot.textWithLabel("Remote name:").getText());
		bot.checkBox("Import projects after clone").deselect();
		bot.button("Finish").click();


		bot.waitUntil(Conditions.shellCloses(shell2), 120000);

		Repository repository = new Repository(new File(destRepo, ".git"));
		assertNotNull(repository.resolve("origin/master"));
		assertEquals(repository.resolve("origin/master"), repository
				.resolve("origin/master"));
		assertEquals("90b818e596660b813b6fcf68f1e9e9b62c615130", repository
				.resolve("refs/tags/v0.4.0").name());
		assertTrue(repository.getAllRefs().size() >= 10);
		assertTrue(new File(destRepo, "README").exists());
		assertFalse(repository.getIndex().isChanged());
		assertFalse(repository.getIndex().getEntry("README").isModified(
				destRepo));
		assertEquals(0,
				ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
	}

	private void goToImportGitRepository() {
		bot.view("Package Explorer").show();
		bot.menu("File").click();
		bot.menu("File").menu("Import...").click();
		bot.shell("Import");

		SWTBotTree tree = bot.tree(0);
		tree.expandNode("Git").select("Git Repository");

		bot.button("Next >").click();
		bot.shell("Import Git Repository");
	}

	/**
	 * Basically the same as testClone, but using the same parameters will not
	 * work because the destination already exists and so the UI will not let
	 * the user finish the operation. This tests that case for default
	 * parameters.
	 *
	 * @throws Exception
	 */
	public void testCloneDestExists() throws Exception {
		File destRepo = new File(new File(ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().getParent(),
				"junit-workspace"), "egit");
		if (!destRepo.exists()) {
			if (!destRepo.mkdirs())
				throw new IOException("Ooops, failed to create " + destRepo);
			new FileOutputStream(new File(destRepo, "afile")).close();
		}
		goToImportGitRepository();
		bot.button("Next >").click();

		bot.button("Next >").click();

		bot.checkBox("Import projects after clone").deselect();
		assertEquals(" " + bot.textWithLabel("Directory:").getText()
				+ " is not an empty directory.", bot.text(2/* ! */).getText());

		bot.activeShell().close();
	}

	/**
	 * Alter some parameters during clone
	 * @throws IOException
	 */
	public void testCloneChangeSomeParameters() throws IOException {
		goToImportGitRepository();
		assertSourceParams(null, "repo.or.cz", "/egit.git", "git", "", true, "",
				"", false, false);
		bot.button("Next >").click();

		for (int i = 0; i < bot.table().rowCount(); ++i)
			bot.table().getTableItem(i).uncheck();
		assertEquals(" At least one branch must be selected.", bot.text(0)
				.getText());
		assertFalse(bot.button("Next >").isEnabled());
		assertTrue(bot.button("Next >").isEnabled());
		bot.button("Next >").click();

		File destRepo = new File(new File(ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().getParent(),
				"junit-workspace"), "EGIT2");
		bot.textWithLabel("Directory:").setText(destRepo.toString());
		assertEquals("historical/pre-eclipse", bot.comboBoxWithLabel("Initial branch:")
				.getText());
		bot.comboBoxWithLabel("Initial branch:").setSelection("historical/pre-eclipse");
		assertEquals("origin", bot.textWithLabel("Remote name:").getText());
		bot.textWithLabel("Remote name:").setText("src");
		bot.checkBox("Import projects after clone").deselect();
		bot.button("Finish").click();


		bot.waitUntil(Conditions.shellCloses(shell2), 120000);

		Repository repository = new Repository(new File(destRepo, ".git"));
		assertNotNull(repository.resolve("src/historical/pre-eclipse"));
		assertNull(repository.resolve("src/master"));
		assertEquals(repository.resolve("stable"), repository
				.resolve("src/stable"));
		assertEquals("90b818e596660b813b6fcf68f1e9e9b62c615130", repository
				.resolve("refs/tags/v0.4.0").name());
		assertTrue(repository.getAllRefs().size() >= 10);

	}

	/**
	 * Exercise editing of fields and verify that derived fields are updated and
	 * enabled/disabled properly along with a proper error message when needed.
	 * No clone is actually made.
	 */
	public void testParameterEditing() {
		goToImportGitRepository();
		assertSourceParams(null, "www.jgit.org", "/EGIT", "git", "", true, "",
				"", false, false);

		bot.textWithLabel("URI:").typeText("X");
		assertSourceParams(null, "www.jgit.org", "/EGITX", "git", "", true, "",
				"", false, false);

		assertSourceParams(null, "www.jgit.org", "/EGIT", "git", "", true, "",
				"", false, false);

		assertSourceParams(" User not supported on git protocol.",
				"www.jgit.org", "/EGIT", "git", "", true, "user", "hi", false,
				false);
				"URI:").getText());

		assertSourceParams(null, "www.jgit.org", "/EGIT", "git+ssh", "", true,
				"user", "", true, true);

		assertSourceParams(null, "www.jgit.org", "/EGIT", "git+ssh", "", true,
				"user", "", true, true);

		assertSourceParams(null, "www.jgit.org", "/EGIT", "git+ssh", "33",
				true, "user", "hi", true, true);

		assertSourceParams(" Host required for ssh protocol.", "", "/EGIT",
				"git+ssh", "", true, "", "", true, true);

		if (Platform.getOS().equals(Platform.OS_WIN32))
			assertSourceParams(" " + System.getProperty("user.dir")
					+ "\\.\\some\\place does not exist.", "", "/some/place",
					"file", "", false, "", "", false, false);
		else
			assertSourceParams(" /some/place does not exist.", "",
					"/some/place", "file", "", false, "", "", false, false);


		bot.textWithLabel("Host:").setText("example.com");
				.getText());
		assertSourceParams(null, "example.com", "/EGIT", "git+ssh", "", true,
				"user", "", true, true);

		bot.textWithLabel("User:").setText("gitney");
				.getText());
		assertSourceParams(null, "example.com", "/EGIT", "git+ssh", "", true,
				"gitney", "", true, true);

		bot.textWithLabel("Password:").setText("fsck");
				.getText());
		assertSourceParams(null, "example.com", "/EGIT", "git+ssh", "", true,
				"gitney", "fsck", true, true);

		bot.textWithLabel("Port:").setText("99");
				"URI:").getText());
		assertSourceParams(null, "example.com", "/EGIT", "git+ssh", "99", true,
				"gitney", "fsck", true, true);

		bot.comboBoxWithLabel("Protocol:").setSelection("ftp");
				"URI:").getText());
		assertSourceParams(null, "example.com", "/EGIT", "ftp", "99", true,
				"gitney", "fsck", true, true);

		bot.comboBoxWithLabel("Protocol:").setSelection("git");
				"URI:").getText());
		assertSourceParams(" User not supported on git protocol.",
				"example.com", "/EGIT", "git", "99", true, "gitney", "fsck",
				false, false);

		bot.comboBoxWithLabel("Protocol:").setSelection("file");
				"URI:").getText());
		assertSourceParams(" Host not supported on file protocol.",
				"example.com", "/EGIT", "file", "99", false, "gitney", "fsck",
				false, false);

		if (Platform.getOS().equals(Platform.OS_WIN32))
			bot.textWithLabel("URI:").setText(
		else
			bot.textWithLabel("URI:").setText(
		assertSourceParams(null, "", System.getProperty("user.home").replace(
				'\\', '/'), "file", "", false, "", "", false, false);

		bot.textWithLabel("URI:").setText(System.getProperty("user.home"));
		assertSourceParams(null, "", System.getProperty("user.home").replace(
				'\\', '/'), "file", "", false, "", "", false, false);

		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			bot.textWithLabel("URI:").setText(
					System.getProperty("user.home").replace('\\', '/'));
			assertSourceParams(null, "", System.getProperty("user.home")
					.replace('\\', '/'), "file", "", false, "", "", false,
					false);
		}
		bot.button("Cancel").click();
	}

	/**
	 * Try not giving a hostname that does not exist. Should yield an error and
	 * disable the next/finish buttons.
	 */
	public void testNoSuchHost() {
		goToImportGitRepository();
		assertSourceParams(null, "no.example.com", "/EGIT", "git", "", true,
				"", "", false, false);
		bot.button("Next >").click();
				.getText());
		assertTrue(bot.button("Cancel").isEnabled());
		assertTrue(bot.button("< Back").isEnabled());
		assertFalse(bot.button("Next >").isEnabled());
		assertFalse(bot.button("Finish").isEnabled());
		bot.button("Cancel").click();
	}

	/**
	 * Try connecting to a non-git socket.
	 */
	public void testWrongSocket() {
		goToImportGitRepository();
		assertSourceParams(null, "www.jgit.org", "/EGIT", "git", "80", true,
				"", "", false, false);
		bot.button("Next >").click();
				.getText());
		assertTrue(bot.button("Cancel").isEnabled());
		assertTrue(bot.button("< Back").isEnabled());
		assertFalse(bot.button("Next >").isEnabled());
		assertFalse(bot.button("Finish").isEnabled());
		bot.button("Cancel").click();
	}

	/**
	 * Firewalled socket, no response.
	 */
	public void testTimeoutToHost() {
		goToImportGitRepository();
		assertSourceParams(null, "www.example.com", "/EGIT", "git", "", true,
				"", "", false, false);
		bot.button("Next >").click();
				bot.text(0).getText());
		assertTrue(bot.button("Cancel").isEnabled());
		assertTrue(bot.button("< Back").isEnabled());
		assertFalse(bot.button("Next >").isEnabled());
		assertFalse(bot.button("Finish").isEnabled());
		bot.button("Cancel").click();
	}

	private void assertStartsWith(String expect, String actual) {
		if (actual.length() < expect.length())
			assertEquals("Wrong start of message", expect, actual);
		else
			assertEquals("Wrong start of message", expect, actual.substring(0,
					expect.length()));
	}

	private void assertSourceParams(String message, String expectHost,
			String expectPath, String expectProtocol, String expectPort,
			boolean enablePort, String expectUser, String expectPassword,
			boolean enabledUser, boolean enabledPass) {
		if (message != null) {
			assertEquals(message, bot.text(6).getText());
			assertFalse(bot.button("Next >").isEnabled());
		} else {
			assertEquals("Enter the location of the source repository.", bot
					.text(6).getText());
			assertTrue(bot.button("Next >").isEnabled());
		}
		assertEquals(expectHost, bot.textWithLabel("Host:").getText());
		assertEquals(expectPath, bot.textWithLabel("Repository path:")
				.getText());
		assertEquals(expectProtocol, bot.comboBoxWithLabel("Protocol:")
				.getText());
		assertEquals(expectPort, bot.textWithLabel("Port:").getText());
		assertEquals(enablePort, bot.textWithLabel("Port:").isEnabled());
		assertEquals(expectUser, bot.textWithLabel("User:").getText());
		assertEquals(expectPassword, bot.textWithLabel("Password:").getText());
		assertEquals(enabledUser, bot.textWithLabel("User:").isEnabled());
		assertEquals(enabledPass, bot.label("Password:").isEnabled());
		assertEquals(enabledPass, bot.textWithLabel("Password:").isEnabled());
	}
}
