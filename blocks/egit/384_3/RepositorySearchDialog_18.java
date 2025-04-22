package org.eclipse.egit.ui.internal.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class RepositoryRemotePropertySource implements IPropertySource {

	private final RepositoryConfig myConfig;

	private final String myName;

	private final PropertySheetPage myPage;

	public RepositoryRemotePropertySource(RepositoryConfig config,
			String remoteName, PropertySheetPage page) {
		myConfig = config;
		myName = remoteName;
		myPage = page;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {

		try {
			myConfig.load();
		} catch (IOException e) {
			showExceptionMessage(e);
		} catch (ConfigInvalidException e) {
			showExceptionMessage(e);
		}
		List<IPropertyDescriptor> resultList = new ArrayList<IPropertyDescriptor>();
		PropertyDescriptor desc = new PropertyDescriptor(RepositoriesView.URL,
				UIText.RepositoryRemotePropertySource_RemoteUrlLabel);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.FETCH,
				UIText.RepositoryRemotePropertySource_FetchLabel);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.PUSH,
				UIText.RepositoryRemotePropertySource_PushLabel);
		resultList.add(desc);
		return resultList.toArray(new IPropertyDescriptor[resultList.size()]);
	}

	public Object getPropertyValue(Object id) {
		String[] list = myConfig.getStringList(RepositoriesView.REMOTE, myName,
				(String) id);
		if (list != null && list.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (String s : list) {
				sb.append('[');
				sb.append(s);
				sb.append(']');
			}
			return sb.toString();
		}
		return myConfig.getString(RepositoriesView.REMOTE, myName, (String) id);
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}

	private void showExceptionMessage(Exception e) {
		MessageDialog.openError(myPage.getSite().getShell(),
				UIText.RepositoryRemotePropertySource_ErrorHeader, e
						.getMessage());
	}
}
