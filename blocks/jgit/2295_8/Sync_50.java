
package org.eclipse.jgit.storage.dht;

public interface StreamingCallback<T> extends AsyncCallback<T> {
	public void onPartialResult(T result);
}
