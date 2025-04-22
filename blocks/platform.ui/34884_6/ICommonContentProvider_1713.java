
package org.eclipse.ui.navigator;

import org.eclipse.ui.IMemento;

public interface ICommonContentExtensionSite {

	IExtensionStateModel getExtensionStateModel();

	IMemento getMemento(); 
  
	INavigatorContentExtension getExtension();
	
	INavigatorContentService getService();
}
