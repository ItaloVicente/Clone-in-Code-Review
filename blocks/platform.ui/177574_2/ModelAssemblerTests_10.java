
package org.eclipse.e4.ui.workbench.modeling;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface IModelProcessorContribution extends Predicate<ApplyAttribute> {

	default Class<?> getProcessorClass() {
		return null;
	}

	default boolean isBeforeFragment() {
		return true;
	}

	@Override
	default boolean test(ApplyAttribute apply) {
		return apply == ApplyAttribute.ALWAYS;
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
