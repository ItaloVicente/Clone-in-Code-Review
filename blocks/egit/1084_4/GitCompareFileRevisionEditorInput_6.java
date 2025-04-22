package org.eclipse.egit.ui.internal;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;

public class FileRevisionTypedElement extends StorageTypedElement {

	private IFileRevision fileRevision;

	private String author;

	public FileRevisionTypedElement(IFileRevision fileRevision) {
		this(fileRevision, null);
	}

	public FileRevisionTypedElement(IFileRevision fileRevision,
			String localEncoding) {
		super(localEncoding);
		Assert.isNotNull(fileRevision);
		this.fileRevision = fileRevision;
	}

	public String getName() {
		return fileRevision.getName();
	}

	protected IStorage fetchContents(IProgressMonitor monitor)
			throws CoreException {
		return fileRevision.getStorage(monitor);

	}

	public String getContentIdentifier() {
		return fileRevision.getContentIdentifier();
	}

	public String getTimestamp() {
		long date = fileRevision.getTimestamp();
		Date dateFromLong = new Date(date);
		return DateFormat.getDateTimeInstance().format(dateFromLong);
	}

	public IFileRevision getFileRevision() {
		return fileRevision;
	}

	public String getPath() {
		URI uri = fileRevision.getURI();
		if (uri != null)
			return uri.getPath();
		return getName();
	}

	public IEditorInput getDocumentKey(Object element) {
		if (element == this && getBufferedStorage() != null) {
			return new FileRevisionEditorInput(fileRevision,
					getBufferedStorage(), getLocalEncoding());
		}
		return null;
	}

	public int hashCode() {
		return fileRevision.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof FileRevisionTypedElement) {
			FileRevisionTypedElement other = (FileRevisionTypedElement) obj;
			return other.getFileRevision().equals(getFileRevision());
		}
		return false;
	}

	public String getAuthor() {
		if (author == null)
			author = fileRevision.getAuthor();
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void fetchAuthor(IProgressMonitor monitor) throws CoreException {
		if (getAuthor() == null && fileRevision.isPropertyMissing()) {
			IFileRevision other = fileRevision.withAllProperties(monitor);
			author = other.getAuthor();
		}
	}

	public IFileRevision getRevision() {
		return fileRevision;
	}
}
