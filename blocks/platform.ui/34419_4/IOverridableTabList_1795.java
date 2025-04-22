
package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.ui.IActionBars;

public interface IActionProvider {

    public void setActionBars(ITabbedPropertySheetPageContributor contributor,
            IActionBars actionBars);
}
