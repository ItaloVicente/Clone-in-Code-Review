package org.eclipse.egit.ui.internal;

import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

class EditableSharedDocumentAdapter extends SharedDocumentAdapter
		implements IElementStateListener {

	private int connectionCount;

	private final ISharedDocumentAdapterListener listener;

	private IEditorInput bufferedKey;

	public interface ISharedDocumentAdapterListener {

		void handleDocumentConnected();

		void handleDocumentDisconnected();

		void handleDocumentFlushed();

		void handleDocumentDeleted();

		void handleDocumentSaved();
	}

	public EditableSharedDocumentAdapter(ISharedDocumentAdapterListener listener) {
		super();
		this.listener = listener;
	}

	public void connect(IDocumentProvider provider, IEditorInput documentKey)
			throws CoreException {
		super.connect(provider, documentKey);
		connectionCount++;
		if (connectionCount == 1) {
			provider.addElementStateListener(this);
			listener.handleDocumentConnected();
		}
	}

	public void disconnect(IDocumentProvider provider, IEditorInput documentKey) {
		try {
			super.disconnect(provider, documentKey);
		} finally {
			if (connectionCount > 0)
				connectionCount--;
			if (connectionCount == 0) {
				provider.removeElementStateListener(this);
				listener.handleDocumentDisconnected();
			}
		}
	}

	public boolean isConnected() {
		return connectionCount > 0;
	}

	public boolean saveDocument(IEditorInput input, boolean overwrite,
			IProgressMonitor monitor) throws CoreException {
		if (isConnected()) {
			IDocumentProvider provider = SharedDocumentAdapter
					.getDocumentProvider(input);
			try {
				saveDocument(provider, input, provider.getDocument(input),
						overwrite, monitor);
			} finally {
				releaseBuffer();
			}
			return true;
		}
		return false;
	}

	public void releaseBuffer() {
		if (bufferedKey != null) {
			IDocumentProvider provider = SharedDocumentAdapter
					.getDocumentProvider(bufferedKey);
			provider.disconnect(bufferedKey);
			bufferedKey = null;
		}
	}

	public void flushDocument(IDocumentProvider provider,
			IEditorInput documentKey, IDocument document, boolean overwrite)
			throws CoreException {
		if (!hasBufferedContents()) {
			bufferedKey = documentKey;
			provider.connect(bufferedKey);
		}
		this.listener.handleDocumentFlushed();
	}

	public void elementContentAboutToBeReplaced(Object element) {
	}

	public void elementContentReplaced(Object element) {
	}

	public void elementDeleted(Object element) {
		listener.handleDocumentDeleted();
	}

	public void elementDirtyStateChanged(Object element, boolean isDirty) {
		if (!isDirty) {
			this.listener.handleDocumentSaved();
		}
	}

	public void elementMoved(Object originalElement, Object movedElement) {
	}

	public boolean hasBufferedContents() {
		return bufferedKey != null;
	}
}
