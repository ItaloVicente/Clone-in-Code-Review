package org.eclipse.ui.dialogs;

import com.ibm.icu.text.MessageFormat;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PreferenceLinkArea extends Object {

    private Link pageLink;

    public PreferenceLinkArea(Composite parent, int style, final String pageId,
            String message, final IWorkbenchPreferenceContainer pageContainer,
            final Object pageData) {
        pageLink = new Link(parent, style);

        IPreferenceNode node = getPreferenceNode(pageId);
        String result;
        if (node == null) {
			result = NLS.bind(
                    WorkbenchMessages.PreferenceNode_NotFound, pageId);
		} else {
			result = MessageFormat.format(message, node.getLabelText());
            
            pageLink.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetSelected(SelectionEvent e) {
                    pageContainer.openPage(pageId, pageData);
                }
            });
        }
        pageLink.setText(result);

    }

    private IPreferenceNode getPreferenceNode(String pageId) {
        Iterator iterator = PlatformUI.getWorkbench().getPreferenceManager()
                .getElements(PreferenceManager.PRE_ORDER).iterator();
        while (iterator.hasNext()) {
            IPreferenceNode next = (IPreferenceNode) iterator.next();
            if (next.getId().equals(pageId)) {
				return next;
			}
        }
        return null;
    }

    public Control getControl() {
        return pageLink;
    }
}
