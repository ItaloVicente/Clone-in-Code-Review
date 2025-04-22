package org.eclipse.egit.ui.test;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withRegex;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.egit.ui.UIProjectUtils;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.hamcrest.Matcher;
import org.junit.Test;

public class UIProjectUtilsTest extends LocalRepositoryTestCase {

	
	@Test
	public void openImportProjectsDialog() throws Exception {
		File repositoryFile = createProjectAndCommitToRepository();
		final Repository repository = new FileRepository(repositoryFile);
		
		bot.getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					UIProjectUtils.openImportProjectsDialog(bot.activeShell().widget, repository);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		});
		Matcher<Shell> withRegex = withRegex("Import Projects from Git Repository.*");
		WaitForObjectCondition<Shell> waitForShell = Conditions.waitForShell(withRegex);
		
		bot.waitUntil(waitForShell);
		
		SWTBotShell shell = new SWTBotShell(waitForShell.get(0));
		TableCollection selection = shell.bot().tree().selection();
		
		assertEquals(1,selection.rowCount());
		
		assertEquals("Working directory - "+repositoryFile.getParentFile().getAbsolutePath(),selection.get(0).get(0));
	}
}
