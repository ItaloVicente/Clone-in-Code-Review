
package org.eclipse.e4.ui.workbench.modeling;

import java.util.ArrayList;
import java.util.List;

public interface IModelProcessorContribution {

	String BEFORE_FRAGMENT_PROPERTY_KEY = "beforefragment"; //$NON-NLS-1$
	String BEFORE_FRAGMENT_PROPERTY_PREFIX = "beforefragment:Boolean="; //$NON-NLS-1$
	String APPLY_PROPERTY_KEY = "apply"; //$NON-NLS-1$
	String APPLY_PROPERTY_PREFIX = "apply="; //$NON-NLS-1$
	String APPLY_ALWAYS = "always"; //$NON-NLS-1$
	String APPLY_INITIAL = "initial"; //$NON-NLS-1$

	default Class<?> getProcessorClass() {
		return null;
	}

	default List<ModelElement> getModelElements() {
		return new ArrayList<>();
	}

	class ModelElement {
		private final String id;
		private final String contextKey;

		public ModelElement(String id) {
			this(id, null);
		}

		public ModelElement(String id, String contextKey) {
			this.id = id;
			this.contextKey = contextKey;
		}

		public String getId() {
			return id;
		}

		public String getContextKey() {
			return contextKey;
		}

	}
}
