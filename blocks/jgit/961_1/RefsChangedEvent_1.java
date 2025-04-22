
package org.eclipse.jgit.events;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListenerList {
	private final ConcurrentMap<Class<? extends RepositoryListener>

	public ListenerHandle addIndexChangedListener(IndexChangedListener listener) {
		return addListener(IndexChangedListener.class
	}

	public ListenerHandle addRefsChangedListener(RefsChangedListener listener) {
		return addListener(RefsChangedListener.class
	}

	public <T extends RepositoryListener> ListenerHandle addListener(
			Class<T> type
		ListenerHandle handle = new ListenerHandle(this
		add(handle);
		return handle;
	}

	@SuppressWarnings("unchecked")
	public void dispatch(RepositoryEvent event) {
		List<ListenerHandle> list = lists.get(event.getListenerType());
		if (list != null) {
			for (ListenerHandle handle : list)
				event.dispatch(handle.listener);
		}
	}

	private void add(ListenerHandle handle) {
		List<ListenerHandle> list = lists.get(handle.type);
		if (list == null) {
			CopyOnWriteArrayList<ListenerHandle> newList;

			newList = new CopyOnWriteArrayList<ListenerHandle>();
			list = lists.putIfAbsent(handle.type
			if (list == null)
				list = newList;
		}
		list.add(handle);
	}

	void remove(ListenerHandle handle) {
		List<ListenerHandle> list = lists.get(handle.type);
		if (list != null)
			list.remove(handle);
	}
}
