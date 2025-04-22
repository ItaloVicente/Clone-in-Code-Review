
package org.eclipse.jgit.storage.dht;

public interface AsyncCallback<T> {
	public void onSuccess(T object);

	public void onFailure(DhtException error);
}
