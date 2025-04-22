package org.eclipse.egit.ui.performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.op.CloneOperation;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.op.ListRemoteOperation;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.push.PushOperationUI;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class LocalRepositoryTestCase extends EGitTestCase {

	private static File testDirectory;

	protected static final String REPO1 = "FirstRepository";

	protected static final String REPO2 = "RemoteRepository";

	protected static final String CHILDREPO = "ChildRepository";

	protected static final String PROJ1 = "GeneralProject";

	protected static final String PROJ2 = "ProjectWithoutDotProject";

	protected static final String FILE1 = "test.txt";

	protected static final String FILE2 = "test2.txt";

	protected static final String FOLDER = "folder";

	public static File getTestDirectory() {
		return testDirectory;
	}
	
	@BeforeClass
	public static void beforeClassBase() throws Exception {
		deleteAllProjects();
		File userHome = FS.DETECTED.userHome();
		testDirectory = new File(userHome, "LocalRepositoriesTests");
		if (testDirectory.exists())
			FileUtils.delete(testDirectory, FileUtils.RECURSIVE
					| FileUtils.RETRY);
		if (!testDirectory.exists())
			FileUtils.mkdir(testDirectory, true);
		File repoRoot = new File(testDirectory, "RepositoryRoot");
		if (!repoRoot.exists())
			FileUtils.mkdir(repoRoot, true);
		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.DEFAULT_REPO_DIR, repoRoot.getPath());
		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SHOW_INITIAL_CONFIG_DIALOG, false);
		org.eclipse.egit.ui.Activator
				.getDefault()
				.getPreferenceStore()
				.setValue(UIPreferences.SHOW_DETACHED_HEAD_WARNING,
						false);
	}

	@AfterClass
	public static void afterClassBase() throws Exception {
		new Eclipse().reset();
		deleteAllProjects();
		shutDownRepositories();
		FileUtils.delete(testDirectory, FileUtils.RECURSIVE | FileUtils.RETRY);
		Activator.getDefault().getRepositoryCache().clear();
	}

	private static void shutDownRepositories() {
		RepositoryCache cache = Activator.getDefault().getRepositoryCache();
		for(Repository repository:cache.getAllRepositories())
			repository.close();
		cache.clear();
	}

	protected static void deleteAllProjects() throws Exception {
		for (IProject prj : ResourcesPlugin.getWorkspace().getRoot()
				.getProjects())
			if (prj.getName().equals(PROJ1))
				prj.delete(false, false, null);
			else if (prj.getName().equals(PROJ2)) {
				FileUtils.delete(EFS.getStore(
						prj.getFile(".project").getLocationURI()).toLocalFile(
						EFS.NONE, null), FileUtils.RETRY);
				prj.delete(false, false, null);
			}

	}

	public static File createProjectAndCommitToRepository() throws Exception {
		return createProjectAndCommitToRepository(REPO1);
	}

	protected static File createProjectAndCommitToRepository(String repoName)
			throws Exception {

		File gitDir = new File(new File(testDirectory, repoName),
				Constants.DOT_GIT);
		Repository myRepository = new FileRepository(gitDir);
		myRepository.create();

		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		if (firstProject.exists())
			firstProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(PROJ1);
		desc.setLocation(new Path(new File(myRepository.getWorkTree(), PROJ1)
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
		desc.setLocation(new Path(new File(myRepository.getWorkTree(), PROJ2)
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
		FileRepository myRepository = lookupRepository(repositoryDir);
		File gitDir = new File(testDirectory, REPO2);
		Repository myRemoteRepository = new FileRepository(gitDir);
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
		RemoteConfig config = new RemoteConfig(myRepository.getConfig(), "push");
		PushOperationUI pa = new PushOperationUI(myRepository, config, 0, false);
		pa.execute(null);

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
				"refs/heads/master", "origin", 0);
		clop.run(null);
		return new File(workdir, Constants.DOT_GIT);
	}

	protected static void createStableBranch(Repository myRepository)
			throws IOException {
		String newRefName = "refs/heads/stable";
		RefUpdate updateRef = myRepository.updateRef(newRefName);
		Ref sourceBranch = myRepository.getRef("refs/heads/master");
		ObjectId startAt = sourceBranch.getObjectId();
		String startBranch = Repository.shortenRefName(sourceBranch.getName());
		updateRef.setNewObjectId(startAt);
		updateRef
				.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
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
		for (File file : myRepository.getWorkTree().listFiles()) {
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

	protected static FileRepository lookupRepository(File directory)
			throws Exception {
		return (FileRepository) org.eclipse.egit.core.Activator.getDefault()
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
			byte[] bytes = IO.readFully(file.getLocation().toFile());
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

	protected static Collection<Ref> getRemoteRefs(URIish uri) throws Exception {
		final Repository db = new FileRepository(new File("/tmp")); //$NON-NLS-1$
		int timeout = 20;
		ListRemoteOperation listRemoteOp = new ListRemoteOperation(db, uri,
				timeout);
		listRemoteOp.run(null);
		return listRemoteOp.getRemoteRefs();
	}
}
