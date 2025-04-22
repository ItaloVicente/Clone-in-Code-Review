package org.eclipse.egit.ui.internal.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewContentProvider;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewLabelProvider;
import org.eclipse.egit.ui.internal.repository.tree.LocalBranchesNode;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteBranchesNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;
import org.eclipse.egit.ui.internal.repository.tree.TagsNode;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public abstract class AbstractBranchSelectionDialog extends TitleAreaDialog {

	protected final Repository repo;

	protected TreeViewer branchTree;

	private String selectedBranch;

	private final String refToMark;

	private final RepositoryTreeNode<Repository> localBranches;

	private final RepositoryTreeNode<Repository> remoteBranches;

	private final RepositoryTreeNode<Repository> tags;

	public AbstractBranchSelectionDialog(Shell parentShell,
			Repository repository) {
		this(parentShell, repository, null);
	}

	public AbstractBranchSelectionDialog(Shell parentShell,
			Repository repository, String refToMark) {
		super(parentShell);
		this.repo = repository;
		localBranches = new LocalBranchesNode(null, this.repo);
		remoteBranches = new RemoteBranchesNode(null, this.repo);
		tags = new TagsNode(null, this.repo);
		this.refToMark = refToMark;
	}

	protected abstract void refNameSelected(String refName);

	protected abstract String getTitle();

	protected abstract String getMessageText();

	protected String getWindowTitle() {
		return getTitle();
	}

	@Override
	protected final Composite createDialogArea(Composite base) {
		Composite parent = (Composite) super.createDialogArea(base);
		parent.setLayout(GridLayoutFactory.fillDefaults().create());

		FilteredTree tree = new FilteredTree(parent, SWT.SINGLE | SWT.BORDER,
				new PatternFilter());
		branchTree = tree.getViewer();
		branchTree.setLabelProvider(new RepositoriesViewLabelProvider());
		branchTree.setContentProvider(new RepositoriesViewContentProvider());

		GridDataFactory.fillDefaults().grab(true, true).hint(500, 300).applyTo(
				tree);
		branchTree.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				String refName = refNameFromDialog();
				refNameSelected(refName);
			}
		});

		branchTree.addOpenListener(new IOpenListener() {

			public void open(OpenEvent event) {
				RepositoryTreeNode node = (RepositoryTreeNode) ((IStructuredSelection) branchTree
						.getSelection()).getFirstElement();
				if (node.getType() != RepositoryTreeNodeType.REF
						&& node.getType() != RepositoryTreeNodeType.TAG)
					branchTree.setExpandedState(node, !branchTree
							.getExpandedState(node));
				else if (getButton(Window.OK).isEnabled())
					okPressed();

			}
		});

		createCustomArea(parent);

		setTitle(getTitle());
		setMessage(getMessageText());
		getShell().setText(getWindowTitle());

		applyDialogFont(parent);

		return parent;
	}

	@Override
	public void create() {
		super.create();

		List<RepositoryTreeNode> roots = new ArrayList<RepositoryTreeNode>();
		roots.add(localBranches);
		roots.add(remoteBranches);
		roots.add(tags);

		branchTree.setInput(roots);

		try {
			if (refToMark != null) {
				if (!markRef(refToMark))
					branchTree.expandToLevel(localBranches, 1);
			} else {
				String fullBranch = repo.getFullBranch();
				if (!markRef(fullBranch))
					branchTree.expandToLevel(localBranches, 1);
			}
		} catch (IOException e) {
		}
	}

	protected boolean markRef(String refName) {
		if (refName == null)
			return false;

		RepositoryTreeNode node;
		try {
			if (refName.startsWith(Constants.R_HEADS)) {
				Ref ref = this.repo.getRef(refName);
				node = new RefNode(localBranches, this.repo, ref);
			} else {
				String mappedRef = Activator.getDefault().getRepositoryUtil()
						.mapCommitToRef(this.repo, refName, false);
				if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_REMOTES)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new RefNode(remoteBranches, this.repo, ref);
				} else if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_TAGS)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new TagNode(tags, this.repo, ref);
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			return false;
		}

		branchTree.setSelection(new StructuredSelection(node), true);
		return true;
	}

	public String getRefName() {
		return this.selectedBranch;
	}

	@Override
	protected void okPressed() {
		this.selectedBranch = refNameFromDialog();
		super.okPressed();
	}

	protected String refNameFromDialog() {
		IStructuredSelection sel = (IStructuredSelection) branchTree
				.getSelection();
		if (sel.size() != 1)
			return null;
		RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
		if (node.getType() == RepositoryTreeNodeType.REF
				|| node.getType() == RepositoryTreeNodeType.TAG) {
			return ((Ref) node.getObject()).getName();
		}
		return null;
	}

	protected void createCustomArea(Composite parent) {
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

}
