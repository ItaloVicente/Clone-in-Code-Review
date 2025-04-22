
package org.eclipse.ui.statushandlers;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.PlatformUI;

public interface IStatusAdapterConstants {

	static final String PROPERTY_PREFIX = PlatformUI.PLUGIN_ID
			+ ".workbench.statusHandlers.adapters"; //$NON-NLS-1$

	public static final QualifiedName TITLE_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "title"); //$NON-NLS-1$

	public static final QualifiedName TIMESTAMP_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "timestamp"); //$NON-NLS-1$

	public static final QualifiedName EXPLANATION_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "explanation"); //$NON-NLS-1$

	public static final QualifiedName HINT_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "suggestedAction"); //$NON-NLS-1$

}
