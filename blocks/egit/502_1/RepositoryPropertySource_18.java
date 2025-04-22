package org.eclipse.egit.ui.internal.repository;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class RepositoriesViewLabelProvider extends BaseLabelProvider implements
		ITableLabelProvider {


	RepositoriesViewLabelProvider(final TreeViewer viewer) {

		viewer.setLabelProvider(this);
		Tree tree = viewer.getTree();
		TreeColumn col = new TreeColumn(tree, SWT.NONE);
		col.setWidth(400);


	}

	public Image getColumnImage(Object element, int columnIndex) {
		return decorateImage(
				((RepositoryTreeNode) element).getType().getIcon(), element);
	}

	public String getColumnText(Object element, int columnIndex) {

		RepositoryTreeNode node = (RepositoryTreeNode) element;
		switch (node.getType()) {
		case REPO:
			File directory = ((Repository) node.getObject()).getDirectory();
			return (directory.getParentFile().getName() + " - " + directory //$NON-NLS-1$
					.getAbsolutePath());
		case FILE:
		case FOLDER: // fall through
			return ((File) node.getObject()).getName();
		case BRANCHES:
			return UIText.RepositoriesView_Branches_Nodetext;
		case REMOTES:
			return UIText.RepositoriesView_RemotesNodeText;
		case REMOTE:
			return (String) node.getObject();
		case PROJECTS:
			return UIText.RepositoriesView_ExistingProjects_Nodetext;
		case REF:
			Ref ref = (Ref) node.getObject();
			String refName = node.getRepository().shortenRefName(ref.getName());
			if (ref.isSymbolic()) {
				refName = refName
						+ " - " //$NON-NLS-1$
						+ node.getRepository().shortenRefName(
								ref.getLeaf().getName());
			}
			return refName;
		case PROJ:

			File file = (File) node.getObject();
			return file.getName();

		case WORKINGDIR:

			return UIText.RepositoriesView_WorkingDir_treenode
					+ " - " //$NON-NLS-1$
					+ node.getRepository().getWorkDir().getAbsolutePath();

		default:
			return null;
		}
	}

	private Image decorateImage(final Image image, Object element) {

		RepositoryTreeNode node = (RepositoryTreeNode) element;
		switch (node.getType()) {

		case REF:
			Ref ref = (Ref) node.getObject();
			String refName = node.getRepository().shortenRefName(ref.getName());
			try {
				String branch = node.getBranch();
				if (refName.equals(branch)) {
					CompositeImageDescriptor cd = new CompositeImageDescriptor() {

						@Override
						protected Point getSize() {
							return new Point(image.getBounds().width, image
									.getBounds().width);
						}

						@Override
						protected void drawCompositeImage(int width, int height) {
							drawImage(image.getImageData(), 0, 0);
							drawImage(UIIcons.OVR_CHECKEDOUT.getImageData(), 0,
									0);

						}
					};
					return cd.createImage();
				}
			} catch (IOException e1) {
			}
			return image;

		case PROJ:

			File file = (File) node.getObject();

			for (IProject proj : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (proj.getLocation().equals(new Path(file.getAbsolutePath()))) {
					CompositeImageDescriptor cd = new CompositeImageDescriptor() {

						@Override
						protected Point getSize() {
							return new Point(image.getBounds().width, image
									.getBounds().width);
						}

						@Override
						protected void drawCompositeImage(int width, int height) {
							drawImage(image.getImageData(), 0, 0);
							drawImage(UIIcons.OVR_CHECKEDOUT.getImageData(), 0,
									0);

						}
					};
					return cd.createImage();
				}
			}
			return image;

		default:
			return image;
		}
	}

}
