package org.eclipse.egit.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.ResourceNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorInput;

class LocalResourceTypedElement extends ResourceNode implements
		IAdaptable {

	private boolean fDirty = false;

	private EditableSharedDocumentAdapter sharedDocumentAdapter;

	private long timestamp;

	private boolean exists;

	private boolean useSharedDocument = true;

	private EditableSharedDocumentAdapter.ISharedDocumentAdapterListener sharedDocumentListener;

	public LocalResourceTypedElement(IResource resource) {
		super(resource);
		exists = resource.exists();
	}

	public void setContent(byte[] contents) {
		fDirty = true;
		super.setContent(contents);
	}

	public void commit(IProgressMonitor monitor) throws CoreException {
		if (isDirty()) {
			if (isConnected()) {
				saveDocument(true, monitor);
			} else {
				IResource resource = getResource();
				if (resource instanceof IFile) {
					ByteArrayInputStream is = null;
					try {
						is = new ByteArrayInputStream(getContent());
						IFile file = (IFile) resource;
						if (file.exists())
							file.setContents(is, false, true, monitor);
						else
							file.create(is, false, monitor);
						fDirty = false;
					} finally {
						fireContentChanged();
						if (is != null)
							try {
								is.close();
							} catch (IOException ex) {
							}
					}
				}
				updateTimestamp();
			}
		}
	}

	public InputStream getContents() throws CoreException {
		if (exists)
			return super.getContents();
		return null;
	}

	public Object getAdapter(Class adapter) {
		if (adapter == ISharedDocumentAdapter.class) {
			if (isSharedDocumentsEnable())
				return getSharedDocumentAdapter();
			else
				return null;
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	private synchronized ISharedDocumentAdapter getSharedDocumentAdapter() {
		if (sharedDocumentAdapter == null)
			sharedDocumentAdapter = new EditableSharedDocumentAdapter(
					new EditableSharedDocumentAdapter.ISharedDocumentAdapterListener() {
						public void handleDocumentConnected() {
							LocalResourceTypedElement.this.updateTimestamp();
							if (sharedDocumentListener != null)
								sharedDocumentListener
										.handleDocumentConnected();
						}

						public void handleDocumentFlushed() {
							LocalResourceTypedElement.this.fireContentChanged();
							if (sharedDocumentListener != null)
								sharedDocumentListener.handleDocumentFlushed();
						}

						public void handleDocumentDeleted() {
							LocalResourceTypedElement.this.update();
							if (sharedDocumentListener != null)
								sharedDocumentListener.handleDocumentDeleted();
						}

						public void handleDocumentSaved() {
							LocalResourceTypedElement.this.updateTimestamp();
							if (sharedDocumentListener != null)
								sharedDocumentListener.handleDocumentSaved();
						}

						public void handleDocumentDisconnected() {
							if (sharedDocumentListener != null)
								sharedDocumentListener
										.handleDocumentDisconnected();
						}
					});
		return sharedDocumentAdapter;
	}

	public boolean isEditable() {
		IResource resource = getResource();
		return resource.getType() == IResource.FILE && exists;
	}

	public boolean isConnected() {
		return sharedDocumentAdapter != null
				&& sharedDocumentAdapter.isConnected();
	}

	public boolean saveDocument(boolean overwrite, IProgressMonitor monitor)
			throws CoreException {
		if (isConnected()) {
			IEditorInput input = sharedDocumentAdapter.getDocumentKey(this);
			sharedDocumentAdapter.saveDocument(input, overwrite, monitor);
			updateTimestamp();
			return true;
		}
		return false;
	}

	protected InputStream createStream() throws CoreException {
		InputStream inputStream = super.createStream();
		updateTimestamp();
		return inputStream;
	}

	void updateTimestamp() {
		if (getResource().exists())
			timestamp = getResource().getLocalTimeStamp();
		else
			exists = false;
	}

	private long getTimestamp() {
		return timestamp;
	}

	public int hashCode() {
		return getResource().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof LocalResourceTypedElement) {
			LocalResourceTypedElement otherElement = (LocalResourceTypedElement) obj;
			return otherElement.getResource().equals(getResource())
					&& exists == otherElement.exists;
		}
		return false;
	}

	public void update() {
		exists = getResource().exists();
	}

	public boolean isSynchronized() {
		long current = getResource().getLocalTimeStamp();
		return current == getTimestamp();
	}

	public boolean exists() {
		return exists;
	}

	protected void fireContentChanged() {
		super.fireContentChanged();
	}

	public void discardBuffer() {
		if (sharedDocumentAdapter != null)
			sharedDocumentAdapter.releaseBuffer();
		super.discardBuffer();
	}

	public boolean isSharedDocumentsEnable() {
		return useSharedDocument && getResource().getType() == IResource.FILE
				&& exists;
	}

	public void enableSharedDocument(boolean enablement) {
		this.useSharedDocument = enablement;
	}

	public boolean isDirty() {
		return fDirty
				|| (sharedDocumentAdapter != null && sharedDocumentAdapter
						.hasBufferedContents());
	}

	public void setSharedDocumentListener(
			EditableSharedDocumentAdapter.ISharedDocumentAdapterListener sharedDocumentListener) {
		this.sharedDocumentListener = sharedDocumentListener;
	}

}
