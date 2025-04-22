package org.eclipse.ui.internal;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.internal.util.PrefUtil;

public class LargeDocumentPreferenceHandler {

	private static final IPreferenceStore PREFERENCE_STORE = PrefUtil.getInternalPreferenceStore();

	private long maxFileSize = 0;
	private boolean checkDocumentSize;

	LargeDocumentPreferenceHandler() {
		initMaxFileSize();
	}
	private void initMaxFileSize() {
		maxFileSize = PREFERENCE_STORE.getLong(IPreferenceConstants.LARGE_DOC_SIZE_FOR_EDITORS);
		checkDocumentSize = maxFileSize != 0;
	}

	Optional<String> getEditorForInput(IEditorInput editorInput) {
		if (editorInput instanceof IPathEditorInput) {
			IPathEditorInput pathEditorInput = (IPathEditorInput) editorInput;
			String editorId = null;
			boolean largeDocument = isLargeDocument(pathEditorInput);
			boolean promptUser = largeDocument;
			if (!largeDocument) {
				editorId = getEditorIdForLargeDocument(pathEditorInput);
				boolean isPromptPreferenceValue = isPromptPreferenceValue(editorId);
				if (isPromptPreferenceValue) {
					promptUser = true;
				}
			}
			if (promptUser) {
				IEditorDescriptor editor = getAlternateEditor();
				if (editor == null) {
					return null;
				}
				editorId = editor.getId();
			}
			if (editorId != null && !editorId.isEmpty()) {
				return Optional.of(editorId);
			}
		}
		return Optional.empty();
	}

	private static IEditorDescriptor getAlternateEditor() {
		Shell shell = ProgressManagerUtil.getDefaultParent();
		EditorSelectionDialog dialog = new EditorSelectionDialog(shell) {
			@Override
			protected IDialogSettings getDialogSettings() {
				IDialogSettings result = new DialogSettings("EditorSelectionDialog"); //$NON-NLS-1$
				result.put(EditorSelectionDialog.STORE_ID_INTERNAL_EXTERNAL, true);
				return result;
			}
		};
		dialog.setMessage(WorkbenchMessages.EditorManager_largeDocumentWarning);

		if (dialog.open() == Window.OK)
			return dialog.getSelectedEditor();
		return null;
	}

	boolean isLargeDocument(IPathEditorInput editorInput) {
		if (!checkDocumentSize)
			return false;

		try {
			IPath path = editorInput.getPath();
			File file = new File(path.toOSString());
			return file.length() > maxFileSize;
		} catch (Exception e) {
			return false;
		}
	}

	String getEditorIdForLargeDocument(IPathEditorInput editorInput) {
		String editorId = null;

		try {
			IPath path = editorInput.getPath();
			String[] preferenceValues = getLargeDocumentPreferenceValues(path);

			if (preferenceValues.length > 0) {
				File file = new File(path.toOSString());
				long fileSize = file.length();

				long maxBound = 0;
				for (int i = 0; i < preferenceValues.length; i = i + 2) {
					String boundString = preferenceValues[i + 0];
					String editorIdString = preferenceValues[i + 1];
					long bound = Long.valueOf(boundString);
					if (fileSize >= bound && bound >= maxBound) {
						maxBound = bound;
						editorId = editorIdString;
					}
				}
			}
		} catch (Exception e) {
			WorkbenchPlugin.log("Exception occurred while checking for large file editor", e); //$NON-NLS-1$
		}
		return editorId;
	}

	private String[] getLargeDocumentPreferenceValues(IPath path) {
		String extensionSeparator = "."; //$NON-NLS-1$
		String preferenceDefaultSuffix = "_DEFAULT"; //$NON-NLS-1$
		String valueSeparator = "\\|"; //$NON-NLS-1$

		String[] preferenceValues = new String[0];

		String fileExtension = path.getFileExtension();

		String largeFilePreference = null;

		String preferenceName = IPreferenceConstants.LARGE_FILE_SIZE_EXTENSIONS + extensionSeparator + fileExtension;
		if (fileExtension != null && !fileExtension.isEmpty()) {
			largeFilePreference = PREFERENCE_STORE.getString(preferenceName);
		}

		if (largeFilePreference == null || largeFilePreference.isEmpty()) {
			preferenceName = IPreferenceConstants.LARGE_FILE_SIZE_EXTENSIONS + preferenceDefaultSuffix;
			largeFilePreference = PREFERENCE_STORE.getString(preferenceName);
		}

		if (largeFilePreference != null && !largeFilePreference.isEmpty()) {
			preferenceValues = largeFilePreference.split(valueSeparator);
			if (preferenceValues.length % 2 != 0) {
				String errorMessage = NLS.bind(
						"Expected pairs of values separated by \"{0}\" for preference \"{1}\" with value \"{2}\"", //$NON-NLS-1$
						new String[] { valueSeparator, preferenceName, Arrays.toString(preferenceValues) });
				WorkbenchPlugin.log(new IllegalArgumentException(errorMessage));
				preferenceValues = new String[0];
			}
		}

		return preferenceValues;
	}

	private static boolean isPromptPreferenceValue(String editorId) {
		String promptConstant = IPreferenceConstants.LARGE_FILE_SIZE_EXTENSIONS + "_prompt"; //$NON-NLS-1$
		boolean isPromptPreferenceValue = promptConstant.equals(editorId);
		return isPromptPreferenceValue;
	}
}
