package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.ui.internal.IObjectContributor;

public interface IPropertyPageContributor extends IObjectContributor {
    public PreferenceNode contributePropertyPage(PropertyPageManager manager,
            Object object);
}
