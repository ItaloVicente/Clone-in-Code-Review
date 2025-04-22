
package org.eclipse.e4.ui.workbench.renderers.swt;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Display;

public class ToolItemUpdater {
	Display display = Display.getCurrent();

	List<HandledContributionItem> itemsToCheck = new ArrayList<HandledContributionItem>();
	final List<HandledContributionItem> orphanedToolItems = new ArrayList<HandledContributionItem>();

	void registerItem(HandledContributionItem item) {
		if (!itemsToCheck.contains(item)) {
			itemsToCheck.add(item);
		}
	}

	void removeItem(HandledContributionItem item) {
		itemsToCheck.remove(item);
	}

	public void updateContributionItems() {
		for (final HandledContributionItem hci : itemsToCheck) {
			if (hci.model != null && hci.model.getParent() != null) {
				hci.updateItemEnablement();
			} else {
				orphanedToolItems.add(hci);
			}
		}
		if (!orphanedToolItems.isEmpty()) {
			itemsToCheck.removeAll(orphanedToolItems);
			orphanedToolItems.clear();
		}

	}
}
