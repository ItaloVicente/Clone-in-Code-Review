package org.eclipse.ui.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.ui.IEditorInput;

public class EditorInputPropertyTester extends PropertyTester {

	private static final String CONTENT_TYPE_ID = "contentTypeId"; //$NON-NLS-1$

	private static final String IS_KIND_OF = "kindOf"; //$NON-NLS-1$

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof IEditorInput) {
			IEditorInput editorInput = (IEditorInput) receiver;
			if (CONTENT_TYPE_ID.equals(property)) {
				String identifier = String.valueOf(expectedValue);
				return testContentType(editorInput, identifier, hasArgument(args, IS_KIND_OF));
			}
		}
		return false;
	}

	private boolean testContentType(IEditorInput editorInput, String contentTypeId, boolean isKindOf) {
		IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
		IContentType expected = contentTypeManager.getContentType(contentTypeId);
		if (expected != null) {
			String name = editorInput.getName();
			if (name != null) {
				IContentType actualContentType = contentTypeManager.findContentTypeFor(name);
				if (actualContentType != null) {
					if (isKindOf) {
						return actualContentType.isKindOf(contentTypeManager.getContentType(contentTypeId));
					}
					return contentTypeId.equals(actualContentType.getId());
				}
			}
		}
		return false;
	}

	private boolean hasArgument(Object[] args, String value) {
		if (args == null) {
			return false;
		}
		for (Object arg : args) {
			if (value.equals(arg)) {
				return true;
			}
		}
		return false;
	}

}
