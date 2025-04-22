
package org.eclipse.ui;

import org.eclipse.ui.part.EditorPart;

public interface ISaveablesSource {

	Saveable[] getSaveables();

	Saveable[] getActiveSaveables();
}
