package org.eclipse.egit.ui.internal.history;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.osgi.framework.Bundle;

public class GitCreatePatchWizard extends Wizard {

	private SWTCommit commit;

	private TreeWalk walker;

	private Repository db;

	private LocationPage locationPage;

	private OptionsPage optionsPage;

	private final static int INITIAL_WIDTH = 300;

	private final static int INITIAL_HEIGHT = 150;

	public static void run(IWorkbenchPart part, final SWTCommit commit,
			TreeWalk walker, Repository db) {
		final String title = UIText.GitCreatePatchWizard_CreatePatchTitle;
		final GitCreatePatchWizard wizard = new GitCreatePatchWizard(commit,
				walker, db);
		wizard.setWindowTitle(title);
		WizardDialog dialog = new WizardDialog(part.getSite().getShell(),
				wizard);
		dialog.setMinimumPageSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		dialog.open();
	}

	public GitCreatePatchWizard(SWTCommit commit, TreeWalk walker, Repository db) {
		this.commit = commit;
		this.walker = walker;
		this.db = db;
	}

	@Override
	public void addPages() {
		String pageTitle = UIText.GitCreatePatchWizard_SelectLocationTitle;
		String pageDescription = UIText.GitCreatePatchWizard_SelectLocationDescription;

		locationPage = new LocationPage(pageTitle, pageTitle, null);
		locationPage.setDescription(pageDescription);
		addPage(locationPage);

		pageTitle = UIText.GitCreatePatchWizard_SelectOptionsTitle;
		pageDescription = UIText.GitCreatePatchWizard_SelectOptionsDescription;
		optionsPage = new OptionsPage(pageTitle, pageTitle, null);
		optionsPage.setDescription(pageDescription);
		addPage(optionsPage);
	}

	@Override
	public boolean performFinish() {
		final boolean isGit = optionsPage.gitFormat.getSelection();
		final boolean isFile = locationPage.fsRadio.getSelection();
		final String fileName = locationPage.fsPathText.getText();

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					StringBuilder sb = new StringBuilder();
					DiffFormatter diffFmt = new DiffFormatter();
					try {
						if (isGit)
							writeGitPatch(sb, diffFmt);
						else
							writePatch(sb, diffFmt);

						if (isFile) {
							Writer output = new BufferedWriter(new FileWriter(
									fileName));
							try {
								output.write(sb.toString());
							} finally {
								output.close();
							}
						} else {
							copyToClipboard(sb.toString());
						}
					} catch (IOException e) {
						Activator
							.logError("Patch file could not be written", e); //$NON-NLS-1$
					}
				}
			});
		} catch (InvocationTargetException e) {
			((WizardPage) getContainer().getCurrentPage()).setErrorMessage(e
					.getMessage() == null ? e.getMessage()
					: UIText.GitCreatePatchWizard_InternalError);
			Activator.logError("Patch file was not written", e); //$NON-NLS-1$
			return false;
		} catch (InterruptedException e) {
			Activator.logError("Patch file was not written", e); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	private void copyToClipboard(final String content) {
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				TextTransfer plainTextTransfer = TextTransfer.getInstance();
				Clipboard clipboard = new Clipboard(getShell().getDisplay());
				clipboard.setContents(new String[] { content },
						new Transfer[] { plainTextTransfer });
				clipboard.dispose();
			}
		});
	}

	private void writeGitPatch(StringBuilder sb, DiffFormatter diffFmt)
			throws IOException {

		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US); //$NON-NLS-1$
		dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
		sb.append(UIText.GitHistoryPage_From).append(" ") //$NON-NLS-1$
				.append(commit.getId().getName()).append(" ") //$NON-NLS-1$
				.append(dtfmt.format(Long.valueOf(System.currentTimeMillis())))
				.append("\n"); //$NON-NLS-1$
		sb.append(UIText.GitHistoryPage_From)
				.append(": ") //$NON-NLS-1$
				.append(commit.getAuthorIdent().getName())
				.append(" <").append(commit.getAuthorIdent().getEmailAddress()) //$NON-NLS-1$
				.append(">\n"); //$NON-NLS-1$
		sb.append(UIText.GitHistoryPage_Date).append(": ") //$NON-NLS-1$
				.append(dtfmt.format(commit.getAuthorIdent().getWhen()))
				.append("\n"); //$NON-NLS-1$
		sb.append(UIText.GitHistoryPage_Subject).append(": [PATCH] ") //$NON-NLS-1$
				.append(commit.getShortMessage());

		String message = commit.getFullMessage().substring(
				commit.getShortMessage().length());
		sb.append(message).append("\n\n"); //$NON-NLS-1$

		FileDiff[] diffs = FileDiff.compute(walker, commit);
		for (FileDiff diff : diffs) {
			sb.append("diff --git a").append(IPath.SEPARATOR) //$NON-NLS-1$
					.append(diff.path).append(" b").append(IPath.SEPARATOR) //$NON-NLS-1$
					.append(diff.path).append("\n"); //$NON-NLS-1$
			diff.outputDiff(sb, db, diffFmt, false, false);
		}
		sb.append("\n--\n"); //$NON-NLS-1$
		Bundle bundle = Activator.getDefault().getBundle();
		String name = (String) bundle.getHeaders().get(
				org.osgi.framework.Constants.BUNDLE_NAME);
		String version = (String) bundle.getHeaders().get(
				org.osgi.framework.Constants.BUNDLE_VERSION);
		sb.append(name).append(" ").append(version); //$NON-NLS-1$
	}

	private void writePatch(StringBuilder sb, DiffFormatter diffFmt)
			throws IOException {
		FileDiff[] diffs = FileDiff.compute(walker, commit);
		for (FileDiff diff : diffs) {
			String projectRelativePath = getProjectRelaticePath(diff);
			sb.append("diff --git ").append(projectRelativePath).append(" ") //$NON-NLS-1$ //$NON-NLS-2$
					.append(projectRelativePath).append("\n"); //$NON-NLS-1$
			diff.outputDiff(sb, db, diffFmt, true, true);
		}
	}

	private String getProjectRelaticePath(FileDiff diff) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkDir().getAbsolutePath())
				.append(diff.path);
		IResource resource = root.getFileForLocation(absolutePath);
		return resource.getProjectRelativePath().toString();
	}

	public class LocationPage extends WizardPage {

		private Button cpRadio;

		private Button fsRadio;

		private Text fsPathText;

		private Button fsBrowseButton;

		private boolean canValidate = true;

		private boolean pageValid;

		protected LocationPage(String pageName, String title,
				ImageDescriptor titleImage) {
			super(pageName, title, titleImage);
		}

		public void createControl(Composite parent) {
			final Composite composite = new Composite(parent, SWT.NULL);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 3;
			composite.setLayout(gridLayout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			setControl(composite);

			GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 3;
			cpRadio = new Button(composite, SWT.RADIO);
			cpRadio.setText(UIText.GitCreatePatchWizard_Clipboard);
			cpRadio.setLayoutData(gd);

			fsRadio = new Button(composite, SWT.RADIO);
			fsRadio.setText(UIText.GitCreatePatchWizard_File);

			fsPathText = new Text(composite, SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			fsPathText.setLayoutData(gd);
			fsPathText.setText(createFileName());

			fsBrowseButton = new Button(composite, SWT.PUSH);
			fsBrowseButton.setText(UIText.GitCreatePatchWizard_Browse);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
			Point minSize = fsBrowseButton.computeSize(SWT.DEFAULT,
					SWT.DEFAULT, true);
			data.widthHint = Math.max(widthHint, minSize.x);
			fsBrowseButton.setLayoutData(data);

			fsRadio.addListener(SWT.Selection, new Listener() {

				public void handleEvent(Event event) {
					validatePage();
				}
			});

			fsPathText.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					validatePage();
				}
			});

			fsBrowseButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					final FileDialog dialog = new FileDialog(getShell(),
							SWT.PRIMARY_MODAL | SWT.SAVE);
					if (pageValid) {
						final File file = new File(fsPathText.getText());
						dialog.setFilterPath(file.getParent());
						dialog.setFileName(file.getName());
					} else {
						dialog.setFileName(""); //$NON-NLS-1$
					}
					dialog.setText(""); //$NON-NLS-1$
					final String path = dialog.open();
					if (path != null)
						fsPathText.setText(new Path(path).toOSString());
					validatePage();
				}
			});
		}

		private String createFileName() {
			String name = commit.getShortMessage();

			name = name.trim();
			try {
				name = URLEncoder.encode(name, "UTF-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
			}
			if (name.length() > 80)
				name = name.substring(0, 80);
			while (name.endsWith(".")) //$NON-NLS-1$
				name = name.substring(0, name.length() - 1);
			name = name.concat(".patch"); //$NON-NLS-1$

			String defaultPath = db.getWorkDir().getAbsolutePath();

			return (new File(defaultPath, name)).getPath();
		}

		protected boolean validatePage() {
			if (!canValidate)
				return false;

			if (fsRadio.getSelection())
				pageValid = validateFilesystemLocation();

			if (pageValid) {
				setMessage(null);
				setErrorMessage(null);
			}
			setPageComplete(pageValid);
			return pageValid;
		}

		private boolean validateFilesystemLocation() {
			final String pathString = fsPathText.getText().trim();
			if (pathString.length() == 0
					|| !new Path("").isValidPath(pathString)) { //$NON-NLS-1$
				setErrorMessage(""); //$NON-NLS-1$
				return false;
			}

			final File file = new File(pathString);
			if (!file.isAbsolute()) {
				setErrorMessage(""); //$NON-NLS-1$
				return false;
			}

			if (file.isDirectory()) {
				setErrorMessage(""); //$NON-NLS-1$
				return false;
			}

			if (pathString.endsWith("/") || pathString.endsWith("\\")) { //$NON-NLS-1$//$NON-NLS-2$
				setErrorMessage(""); //$NON-NLS-1$
				return false;
			}

			final File parent = file.getParentFile();
			if (!(parent.exists() && parent.isDirectory())) {
				setErrorMessage(""); //$NON-NLS-1$
				return false;
			}
			return true;
		}
	}

	public class OptionsPage extends WizardPage {

		private Button gitFormat;

		protected OptionsPage(String pageName, String title,
				ImageDescriptor titleImage) {
			super(pageName, title, titleImage);
		}

		public void createControl(Composite parent) {
			final Composite composite = new Composite(parent, SWT.NULL);
			GridLayout gridLayout = new GridLayout();
			setControl(composite);
			gridLayout.numColumns = 3;
			composite.setLayout(gridLayout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 3;
			gitFormat = new Button(composite, SWT.CHECK);
			gitFormat.setText(UIText.GitCreatePatchWizard_GitFormat);
			gitFormat.setLayoutData(gd);
		}

	}

}
