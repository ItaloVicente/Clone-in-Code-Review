package org.eclipse.ui;

public class MultiPartInitException extends WorkbenchException {

	private IWorkbenchPartReference[] references;
	private PartInitException[] exceptions;

	public MultiPartInitException(IWorkbenchPartReference[] references,
			PartInitException[] exceptions) {
		super(exceptions[findFirstException(exceptions)].getStatus());
		this.references = references;
		this.exceptions = exceptions;
	}

	public IWorkbenchPartReference[] getReferences() {
		return references;
	}

	public PartInitException[] getExceptions() {
		return exceptions;
	}

	private static int findFirstException(PartInitException[] exceptions) {
		for (int i = 0; i < exceptions.length; i++) {
			if (exceptions[i] != null)
				return i;
		}
		throw new IllegalArgumentException();
	}

	private static final long serialVersionUID = -9138185942975165490L;

}
