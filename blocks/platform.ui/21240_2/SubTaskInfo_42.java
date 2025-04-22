
package org.eclipse.e4.ui.progress.internal;

import java.util.HashMap;

import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.legacy.StatusAdapter;

public class StatusAdapterHelper {
	private static StatusAdapterHelper instance;

	private HashMap map;

	private StatusAdapterHelper() {
	}

	public static StatusAdapterHelper getInstance() {
		if (instance == null) {
			instance = new StatusAdapterHelper();
		}
		return instance;
	}

	public void putStatusAdapter(JobInfo info, StatusAdapter statusAdapter) {
		if (map == null) {
			map = new HashMap();
		}
		map.put(info, statusAdapter);
	}

	public StatusAdapter getStatusAdapter(JobInfo info) {
		if (map == null) {
			return null;
		}
		StatusAdapter statusAdapter = (StatusAdapter) map.remove(info);
		statusAdapter.setProperty(
				IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
				Boolean.FALSE);
		return statusAdapter;
	}

	public void clear() {
		if (map != null) {
			map.clear();
		}
	}
}
