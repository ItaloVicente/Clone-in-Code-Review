package org.eclipse.egit.ui.internal.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class RepositoryRemotePropertySource implements IPropertySource {

	private final StoredConfig myConfig;

	private final String myName;

	public RepositoryRemotePropertySource(StoredConfig config,
			String remoteName, PropertySheetPage page) {
		myConfig = config;
		myName = remoteName;
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {

		try {
			myConfig.load();
		} catch (IOException e) {
			Activator.handleError(
					UIText.RepositoryRemotePropertySource_ErrorHeader, e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(
					UIText.RepositoryRemotePropertySource_ErrorHeader, e, true);
		}
		List<IPropertyDescriptor> resultList = new ArrayList<>();
		PropertyDescriptor desc = new PropertyDescriptor(RepositoriesView.URL,
				UIText.RepositoryRemotePropertySource_RemoteFetchURL_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.FETCH,
				UIText.RepositoryRemotePropertySource_FetchLabel);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.PUSHURL,
				UIText.RepositoryRemotePropertySource_RemotePushUrl_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.PUSH,
				UIText.RepositoryRemotePropertySource_PushLabel);
		resultList.add(desc);
		return resultList.toArray(new IPropertyDescriptor[resultList.size()]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		String[] list = myConfig.getStringList(RepositoriesView.REMOTE, myName,
				(String) id);
		if (list != null && list.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (String s : list) {
				sb.append('[');
				sb.append(s);
				sb.append(']');
			}
			return sb.toString();
		}
		return myConfig.getString(RepositoriesView.REMOTE, myName, (String) id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

}
