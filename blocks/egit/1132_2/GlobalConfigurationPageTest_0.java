package org.eclipse.egit.ui.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.op.CloneOperation;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.ui.internal.push.PushConfiguredRemoteAction;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.Eclipse;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class LocalRepositoryTestCase extends EGitTestCase {

	protected static File testDirectory;

	protected static final String REPO1 = "FirstRepository";

	protected static final String REPO2 = "RemoteRepository";

	protected static final String CHILDREPO = "ChildRepository";

	protected static final String PROJ1 = "GeneralProject";

	protected static final String PROJ2 = "ProjectWithoutDotProject";

	protected static final String FILE1 = "test.txt";

	protected static final String FILE2 = "test2.txt";

	protected static final String FOLDER = "folder";

	@BeforeClass
	public static void beforeClassBase() throws Exception {
		deleteAllProjects();
		File userHome = FS.DETECTED.userHome();
		testDirectory = new File(userHome, "LocalRepositoriesTests");
		if (testDirectory.exists())
			deleteRecursive(testDirectory);
		testDirectory.mkdir();
	}

	@AfterClass
	public static void afterClassBase() throws Exception {
		new Eclipse().reset();
		deleteAllProjects();
		deleteRecursive(testDirectory);
	}

	protected static void deleteRecursive(File dirOrFile) {
		if (dirOrFile.isDirectory()) {
			for (File file : dirOrFile.listFiles()) {
				deleteRecursive(file);
			}
		}
		boolean deleted = dirOrFile.delete();
		if (!deleted) {
			dirOrFile.deleteOnExit();
		}
	}

	protected static void deleteAllProjects() throws CoreException {
		for (IProject prj : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects())
			if (prj.getName().equals(PROJ1))
				prj.delete(false, false, null);
			else if (prj.getName().equals(PROJ2)) {
				EFS.getStore(prj.getFile(".project").getLocationURI())
						.toLocalFile(EFS.NONE, null).delete();
				prj.delete(false, false, null);
			}

	}

	protected static File createProjectAndCommitToRepository() throws Exception {

		File gitDir = new File(new File(testDirectory, REPO1),
				Constants.DOT_GIT);
		gitDir.mkdir();
		Repository myRepository = new Repository(gitDir);
		myRepository.create();

		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		if (firstProject.exists())
			firstProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(PROJ1);
		desc.setLocation(new Path(new File(myRepository.getWorkDir(), PROJ1)
				.getPath()));
		firstProject.create(desc, null);
		firstProject.open(null);

		IFolder folder = firstProject.getFolder(FOLDER);
		folder.create(false, true, null);
		IFile textFile = folder.getFile(FILE1);
		textFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile textFile2 = folder.getFile(FILE2);
		textFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		new ConnectProviderOperation(firstProject, gitDir).execute(null);

		IProject secondPoject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);

		if (secondPoject.exists())
			secondPoject.delete(true, null);

		desc = ResourcesPlugin.getWorkspace().newProjectDescription(PROJ2);
		desc.setLocation(new Path(new File(myRepository.getWorkDir(), PROJ2)
				.getPath()));
		secondPoject.create(desc, null);
		secondPoject.open(null);

		IFolder secondfolder = secondPoject.getFolder(FOLDER);
		secondfolder.create(false, true, null);
		IFile secondtextFile = secondfolder.getFile(FILE1);
		secondtextFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile secondtextFile2 = secondfolder.getFile(FILE2);
		secondtextFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		new ConnectProviderOperation(secondPoject, gitDir).execute(null);

		IFile[] commitables = new IFile[] { firstProject.getFile(".project"),
				textFile, textFile2, secondtextFile, secondtextFile2 };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, "Initial commit");
		op.execute(null);

		createStableBranch(myRepository);
		touchAndSubmit(null);
		return gitDir;
	}

	protected static File createRemoteRepository(File repositoryDir)
			throws Exception {
		Repository myRepository = lookupRepository(repositoryDir);
		File gitDir = new File(testDirectory, REPO2);
		Repository myRemoteRepository = new Repository(gitDir);
		myRemoteRepository.create();
		assertTrue(myRemoteRepository.isBare());

		createStableBranch(myRepository);

		myRepository.getConfig().setString("remote", "push", "pushurl",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "push", "push",
				"+refs/heads/*:refs/heads/*");

		myRepository.getConfig().setString("remote", "fetch", "url",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "fetch", "fetch",
				"+refs/heads/*:refs/heads/*");

		myRepository.getConfig().setString("remote", "both", "pushurl",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "both", "push",
				"+refs/heads/*:refs/heads/*");
		myRepository.getConfig().setString("remote", "both", "url",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "both", "fetch",
				"+refs/heads/*:refs/heads/*");

		myRepository.getConfig().setString("remote", "mixed", "push",
				"+refs/heads/*:refs/heads/*");
		myRepository.getConfig().setString("remote", "mixed", "url",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "mixed", "fetch",
				"+refs/heads/*:refs/heads/*");

		myRepository.getConfig().save();
		PushConfiguredRemoteAction pa = new PushConfiguredRemoteAction(
				myRepository, "push");

		pa.run(null, false);

		try {
			RefUpdate op = myRepository.updateRef("refs/heads/stable");
			op.setRefLogMessage("branch deleted", //$NON-NLS-1$
					false);
			op.setForceUpdate(true);
			op.delete();
		} catch (IOException ioe) {
			throw new InvocationTargetException(ioe);
		}
		return myRemoteRepository.getDirectory();
	}

	protected static File createChildRepository(File repositoryDir)
			throws Exception {
		Repository myRepository = lookupRepository(repositoryDir);
		URIish uri = new URIish("file:///" + myRepository.getDirectory());
		File workdir = new File(testDirectory, CHILDREPO);
		CloneOperation clop = new CloneOperation(uri, true, null, workdir,
				"refs/heads/master", "origin");
		clop.run(null);
		return new File(workdir, Constants.DOT_GIT);
	}

	protected static void createStableBranch(Repository myRepository)
			throws IOException {
		String newRefName = "refs/heads/stable";
		RefUpdate updateRef = myRepository.updateRef(newRefName);
		Ref sourceBranch = myRepository.getRef("refs/heads/master");
		ObjectId startAt = sourceBranch.getObjectId();
		String startBranch = myRepository
				.shortenRefName(sourceBranch.getName());
		updateRef.setNewObjectId(startAt);
		updateRef
				.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
	}

	protected void assertClickOpens(SWTBotTree tree, String menu, String window)
			throws InterruptedException {
		ContextMenuHelper.clickContextMenu(tree, menu);
		SWTBotShell shell = bot.shell(window);
		shell.activate();
		waitInUI();
		shell.bot().button(IDialogConstants.CANCEL_LABEL).click();
		shell.close();
	}

	protected static void waitInUI() throws InterruptedException {
		Thread.sleep(1000);
	}

	protected void shareProjects(File repositoryDir) throws Exception {
		Repository myRepository = lookupRepository(repositoryDir);
		FilenameFilter projectFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(".project");
			}
		};
		for (File file : myRepository.getWorkDir().listFiles()) {
			if (file.isDirectory()) {
				if (file.list(projectFilter).length > 0) {
					IProjectDescription desc = ResourcesPlugin.getWorkspace()
							.newProjectDescription(file.getName());
					desc.setLocation(new Path(file.getPath()));
					IProject prj = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(file.getName());
					prj.create(desc, null);
					prj.open(null);

					new ConnectProviderOperation(prj, myRepository
							.getDirectory()).execute(null);
				}
			}
		}
	}

	@SuppressWarnings("boxing")
	protected void assertProjectExistence(String projectName, boolean existence) {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		assertEquals("Project existence " + projectName, prj.exists(),
				existence);
	}

	protected static Repository lookupRepository(File directory)
			throws Exception {
		return org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().lookupRepository(directory);
	}

	protected static void touchAndSubmit(String commitMessage) throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PROJ1);
		if (!prj.isAccessible())
			throw new IllegalStateException("No project to touch");
		IFile file = prj.getFile(new Path("folder/test.txt"));
		String newContent = "Touched at " + System.currentTimeMillis();
		file.setContents(new ByteArrayInputStream(newContent.getBytes(prj
				.getDefaultCharset())), 0, null);

		IFile[] commitables = new IFile[] { file };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		String message = commitMessage;
		if (message == null)
			message = newContent;
		waitInUI();
		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, message);
		op.execute(null);
	}

	protected static void addAndCommit(IFile file, String commitMessage)
			throws Exception {
		IProject prj = file.getProject();
		if (!prj.isAccessible())
			throw new IllegalStateException("No project to touch");
		IFile[] commitables = new IFile[] { file };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, commitMessage);
		op.execute(null);
	}

	protected static void setTestFileContent(String newContent)
			throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PROJ1);
		if (!prj.isAccessible())
			throw new IllegalStateException("No project found");
		IFile file = prj.getFile(new Path("folder/test.txt"));
		file.setContents(new ByteArrayInputStream(newContent.getBytes(prj
				.getDefaultCharset())), 0, null);
	}

	protected String getTestFileContent() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt"));
		if (file.exists()) {
			byte[] bytes = new byte[0];
			InputStream is = null;
			try {
				is = file.getContents();
				bytes = new byte[is.available()];
				is.read(bytes);
			} finally {
				if (is != null)
					is.close();
			}
			return new String(bytes, file.getCharset());
		} else {
			return "";
		}
	}

	protected SWTBotTreeItem getProjectItem(SWTBotTree projectExplorerTree,
			String project) {
		for (SWTBotTreeItem item : projectExplorerTree.getAllItems()) {
			String itemText = item.getText();
			StringTokenizer tok = new StringTokenizer(itemText, " ");
			String name = tok.nextToken();
			if (name.equals(">"))
				name = tok.nextToken();
			if (project.equals(name))
				return item;
		}
		return null;
	}

	protected void pressAltAndChar(SWTBotShell shell, char charToPress) {
		Display display = Display.getDefault();
		Event evt = new Event();
		evt.type = SWT.KeyDown;
		evt.item = shell.widget;
		evt.keyCode = SWT.ALT;
		display.post(evt);
		evt.keyCode = 0;
		evt.character = charToPress;
		display.post(evt);
		evt.type = SWT.KeyUp;
		display.post(evt);
		evt.keyCode = SWT.ALT;
		evt.character = ' ';
		display.post(evt);
	}

	protected void activateItemByKeyboard(SWTBotShell shell,
			String itemWithShortcut) {
		int index = itemWithShortcut.indexOf('&');
		if (index >= 0 && index < itemWithShortcut.length())
			pressAltAndChar(shell, itemWithShortcut.charAt(index + 1));
	}
}
