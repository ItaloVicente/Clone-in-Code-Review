/*******************************************************************************
 * Copyright (c) 2006, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.ide.undo;

import java.net.URI;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.internal.ide.undo.ProjectDescription;
import org.eclipse.ui.internal.ide.undo.UndoMessages;

/**
 * A CopyProjectOperation represents an undoable operation for copying a
 * project, also specifying the location of its contents. Clients may call the
 * public API from a background thread.
 *
 * <p>
 * This class is intended to be instantiated and used by clients. It is not
 * intended to be subclassed by clients.
 * </p>
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @since 3.3
 */
public class CopyProjectOperation extends AbstractCopyOrMoveResourcesOperation {

	private URI projectLocation;

	private IProject originalProject;

	private ProjectDescription originalProjectDescription;

	/**
	 * Create a CopyProjectOperation that copies the specified project and sets
	 * its location to the specified location.
	 *
	 * @param project
	 *            the project to be copied
	 * @param name
	 *            the name of the copy
	 * @param location
	 *            the location for the project's content, or <code>null</code>
	 *            if the default project location should be used.
	 * @param label
	 *            the label of the operation
	 */
	public CopyProjectOperation(IProject project, String name, URI location,
			String label) {
		super(new IResource[] { project }, new Path(name), label);
		Assert.isLegal(project != null);
		originalProject = project;
		if (location != null
				&& URIUtil.toPath(location).equals(Platform.getLocation())) {
			projectLocation = null;
		} else {
			projectLocation = location;
		}
	}

	/*
	 * Make a project handle for the proposed target project, or null if one
	 * cannot be made.
	 */
	private IProject getProposedProjectHandle() {
		if (destination.segmentCount() == 1) {
			return getWorkspace().getRoot().getProject(
					destination.lastSegment());
		}
		return null;
	}

	/*
	 * Checks that the specified project location is valid in addition to
	 * superclass checks.
	 */
	@Override
	protected IStatus computeMoveOrCopyStatus() {
		IStatus status = Status.OK_STATUS;
		IProject project = getProposedProjectHandle();
		if (project == null) {
			return getErrorStatus(UndoMessages.AbstractResourcesOperation_NotEnoughInfo);
		}
		if (projectLocation != null) {
			status = getWorkspace().validateProjectLocationURI(project,
					projectLocation);
		}
		if (status.isOK()) {
			return super.computeMoveOrCopyStatus();
		}
		return status;
	}

	/*
	 * Map execute to copying the project
	 */
	@Override
	protected void doExecute(IProgressMonitor monitor, IAdaptable uiInfo)
			throws CoreException {
		IProject newProject = copyProject(originalProject, destination,
				projectLocation, monitor);
		setTargetResources(new IResource[] { newProject });
		setResourceDescriptions(new ResourceDescription[0]);
	}

	/*
	 * Map undo to deleting the project we just copied.
	 */
	@Override
	protected void doUndo(IProgressMonitor monitor, IAdaptable uiInfo)
			throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		WorkspaceUndoUtil.delete(resources, subMonitor.split(1), uiInfo, true);
		setTargetResources(new IResource[] { originalProject });
		setResourceDescriptions(new ResourceDescription[0]);
	}

	@Override
	protected boolean isDestinationPathValid(IResource resource, int index) {
		return true;
	}

	@Override
	protected String getProposedName(IResource resource, int index) {
		return destination.lastSegment();
	}

	@Override
	protected boolean updateResourceChangeDescriptionFactory(
			IResourceChangeDescriptionFactory factory, int operation) {
		boolean update = false;
		if (operation == UNDO) {
			for (int i = 0; i < resources.length; i++) {
				update = true;
				IResource resource = resources[i];
				factory.delete(resource);
			}
		} else {
			factory.copy(originalProject,
					getDestinationPath(originalProject, 0));
		}
		return update;
	}

	/*
	 * This implementation computes the ability to delete the original copy and
	 * restore any overwritten resources.
	 */
	@Override
	public IStatus computeUndoableStatus(IProgressMonitor monitor) {
		IStatus status = super.computeUndoableStatus(monitor);
		if (!status.isOK()) {
			return status;
		}
		if (originalProject == null	|| !originalProjectDescription.verifyExistence(true)) {
			markInvalid();
			return getErrorStatus(UndoMessages.CopyResourcesOperation_NotAllowedDueToDataLoss);
		}
		if (status.isOK()) {
			status = computeDeleteStatus();
		}
		return status;
	}

	/*
	 * Copy the specified project, returning the handle of the copy.
	 */
	IProject copyProject(IProject project, IPath destinationPath,
			URI locationURI, IProgressMonitor monitor) throws CoreException {
		monitor.setTaskName(UndoMessages.AbstractCopyOrMoveResourcesOperation_copyProjectProgress);

		boolean open = project.isOpen();
		if (!open) {
			project.open(null);
		}
		originalProjectDescription = new ProjectDescription(project);
		IProjectDescription description = project.getDescription();

		description.setName(destinationPath.lastSegment());
		description.setLocationURI(locationURI);

		project.copy(description, IResource.FORCE | IResource.SHALLOW, monitor);

		if (!open) {
			project.close(null);
		}
		return (IProject) getWorkspace().getRoot().findMember(destinationPath);
	}
}
