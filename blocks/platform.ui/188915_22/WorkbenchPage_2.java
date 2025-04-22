package org.eclipse.ui.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.internal.util.PrefUtil;

public class LargeFileLimitsPreferenceHandler {

	public static class FileLimit {
		public final String editorId;
		public final long fileSize;

		public FileLimit(String editorId, long fileSize) {
			this.editorId = editorId;
			this.fileSize = fileSize;
		}
	}

	public static final String PROMPT_EDITOR_PREFERENCE_VALUE = IPreferenceConstants.LARGE_FILE_LIMITS + "_prompt"; //$NON-NLS-1$
	private static final long EMPTY_LIMIT_VALUE = Long.MIN_VALUE;

	private static final IPreferenceStore PREFERENCE_STORE = PrefUtil.getInternalPreferenceStore();

	private static final String DISABLED_EXTENSIONS_PREFERENCE = IPreferenceConstants.LARGE_FILE_LIMITS + "_disabled"; //$NON-NLS-1$
	private static final String CONFIGURED_EXTENSIONS_PREFERENCE = IPreferenceConstants.LARGE_FILE_LIMITS + "_types"; //$NON-NLS-1$
	private static final String DEFAULT_PREFERENCE_NAME = IPreferenceConstants.LARGE_FILE_LIMITS + "_DEFAULT"; //$NON-NLS-1$

	private static final String EXTENSION_SEPARATOR = "."; //$NON-NLS-1$
	private static final String PREFERENCE_EXTENSIONS_SEPARATOR = ","; //$NON-NLS-1$
	private static final String PREFERENCE_VALUE_SEPARATOR = ","; //$NON-NLS-1$

	private static final String EMPTY_VALUES = ""; //$NON-NLS-1$

	private final Map<String, List<FileLimit>> preferencesCache;
	private final IPropertyChangeListener preferencesListener;

	private long legacyMaxFileSize = 0;
	private boolean legacyCheckDocumentSize;

	LargeFileLimitsPreferenceHandler() {
		initLegacyPreference();
		preferencesCache = new HashMap<>();
		preferencesListener = e -> {
			String property = e.getProperty();
			if (property.startsWith(IPreferenceConstants.LARGE_FILE_LIMITS)) {
				preferencesCache.clear();
			}
		};
		PREFERENCE_STORE.addPropertyChangeListener(preferencesListener);
	}

	private void initLegacyPreference() {
		legacyMaxFileSize = getLargeDocumentLegacyPreferenceValue();
		legacyCheckDocumentSize = legacyMaxFileSize != 0;
	}

	public void dispose() {
		PREFERENCE_STORE.removePropertyChangeListener(preferencesListener);
	}

	public static void setDefaults() {
		PREFERENCE_STORE.setDefault(DEFAULT_PREFERENCE_NAME, EMPTY_LIMIT_VALUE);
	}

	public static boolean isLargeDocumentLegacyPreferenceSet() {
		long legacyPreferenceValue = getLargeDocumentLegacyPreferenceValue();
		return legacyPreferenceValue > 0;
	}

	public static Long getDefaultLimit() {
		long fileSize = PREFERENCE_STORE.getLong(DEFAULT_PREFERENCE_NAME);
		if (fileSize == EMPTY_LIMIT_VALUE) {
			return null;
		}
		return Long.valueOf(fileSize);
	}

	public static void removeDefaultLimit() {
		setDefaultLimit(EMPTY_LIMIT_VALUE);
	}

	public static void setDefaultLimit(long fileSize) {
		PREFERENCE_STORE.setValue(DEFAULT_PREFERENCE_NAME, fileSize);
	}

	public static String[] getConfiguredExtensionTypes() {
		String[] extensions = getExtensionsPreferenceValue(CONFIGURED_EXTENSIONS_PREFERENCE);
		return extensions;
	}


	public static void setConfiguredExtensionTypes(String[] extensionTypes) {
		setExtensionsPreferenceValue(CONFIGURED_EXTENSIONS_PREFERENCE, extensionTypes);
	}

	public static String[] getDisabledExtensionTypes() {
		String[] extensions = getExtensionsPreferenceValue(DISABLED_EXTENSIONS_PREFERENCE);
		return extensions;
	}

	public static void setDisabledExtensionTypes(String[] extensionTypes) {
		setExtensionsPreferenceValue(DISABLED_EXTENSIONS_PREFERENCE, extensionTypes);
	}

	public static List<FileLimit> getFileLimitsForExtension(String fileExtension) {
		String preferenceName = getPreferenceNameForExtension(fileExtension);
		List<FileLimit> preferenceValues = getPreferenceValues(preferenceName);
		return preferenceValues;
	}

	public static void removeFileLimitsForExtension(String fileExtension) {
		String preferenceName = getPreferenceNameForExtension(fileExtension);
		PREFERENCE_STORE.setValue(preferenceName, EMPTY_VALUES);
	}

	public static void setFileLimitsForExtension(String fileExtension, List<FileLimit> fileLimits) {
		String preferenceName = getPreferenceNameForExtension(fileExtension);
		setPreferenceValues(preferenceName, fileLimits);
	}

	public static boolean isPromptPreferenceValue(String editorId) {
		boolean isPromptPreferenceValue = PROMPT_EDITOR_PREFERENCE_VALUE.equals(editorId);
		return isPromptPreferenceValue;
	}

	Optional<String> getEditorForInput(IEditorInput editorInput) {
		if (editorInput instanceof IPathEditorInput) {
			IPathEditorInput pathEditorInput = (IPathEditorInput) editorInput;
			IPath inputPath = pathEditorInput.getPath();
			return getEditorForPath(inputPath);
		}
		return Optional.empty();
	}

	private Optional<String> getEditorForPath(IPath inputPath) {
		String editorId = null;
		boolean largeFile = isLargeDocumentFromLegacy(inputPath);
		boolean promptUser = largeFile;
		if (!largeFile) {
			editorId = getEditorIdForLargeFile(inputPath);
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

	boolean isLargeDocumentFromLegacy(IPath path) {
		if (!legacyCheckDocumentSize)
			return false;

		try {
			File file = new File(path.toOSString());
			return file.length() > legacyMaxFileSize;
		} catch (Exception e) {
			return false;
		}
	}

	String getEditorIdForLargeFile(IPath path) {
		String editorId = null;

		try {
			List<FileLimit> fileLimits = getFileLimits(path, preferencesCache);

			if (!fileLimits.isEmpty()) {
				File file = new File(path.toOSString());
				long fileSize = file.length();

				long maxBound = 0;
				for (FileLimit fileLimit : fileLimits) {
					String editorIdString = fileLimit.editorId;
					long limit = fileLimit.fileSize;
					if (fileSize > limit && limit > maxBound) {
						maxBound = limit;
						editorId = editorIdString;
					}
				}
			}
		} catch (Exception e) {
			WorkbenchPlugin.log("Exception occurred while checking for large file editor", e); //$NON-NLS-1$
		}
		return editorId;
	}

	private static List<FileLimit> getFileLimits(IPath path, Map<String, List<FileLimit>> preferencesCache) {
		String fileExtension = path.getFileExtension();
		List<FileLimit> preferenceValues = new ArrayList<>();
		if (fileExtension != null) {
			preferenceValues = preferencesCache.get(fileExtension);
			if (preferenceValues == null) {
				preferenceValues = getLargeFilePreferenceValues(fileExtension);
				preferencesCache.put(fileExtension, preferenceValues);
			}
		}
		return preferenceValues;
	}

	private static List<FileLimit> getLargeFilePreferenceValues(String fileExtension) {
		List<FileLimit> preferenceValues = new ArrayList<>();
		String[] disabled = getDisabledExtensionTypes();
		boolean isDisabled = Arrays.asList(disabled).contains(fileExtension);
		if (!isDisabled) {
			String preferenceName = getPreferenceNameForExtension(fileExtension);
			String largeFilePreference = PREFERENCE_STORE.getString(preferenceName);

			if (largeFilePreference == null || largeFilePreference.isEmpty()) {
				Long defaultLimit = getDefaultLimit();
				if (defaultLimit != null && defaultLimit.longValue() != EMPTY_LIMIT_VALUE) {
					FileLimit limit = new FileLimit(PROMPT_EDITOR_PREFERENCE_VALUE, defaultLimit.longValue());
					preferenceValues.add(limit);
				}
			} else {
				preferenceValues = getPreferenceValues(preferenceName);
			}
		}

		return preferenceValues;
	}

	private static long getLargeDocumentLegacyPreferenceValue() {
		return PREFERENCE_STORE.getLong(IPreferenceConstants.LARGE_DOC_SIZE_FOR_EDITORS);
	}

	private static String[] getExtensionsPreferenceValue(String preferenceName) {
		String[] extensions = new String[0];
		String extensionTypes = PREFERENCE_STORE.getString(preferenceName);
		if (extensionTypes != null && !extensionTypes.isEmpty()) {
			extensions = extensionTypes.split(PREFERENCE_EXTENSIONS_SEPARATOR);
		}
		return extensions;
	}

	private static void setExtensionsPreferenceValue(String preferenceName, String[] extensionTypes) {
		String preferenceValue = EMPTY_VALUES;
		if (extensionTypes.length > 0) {
			preferenceValue = String.join(PREFERENCE_EXTENSIONS_SEPARATOR, extensionTypes);
		}
		PREFERENCE_STORE.setValue(preferenceName, preferenceValue);
	}

	private static List<FileLimit> getPreferenceValues(String preferenceName) {
		String largeFilePreference = PREFERENCE_STORE.getString(preferenceName);
		List<FileLimit> preferenceValues = new ArrayList<>();
		if (largeFilePreference != null && !largeFilePreference.isEmpty()) {
			String[] values = splitPreferenceValues(preferenceName, largeFilePreference);
			preferenceValues = parsePreferenceValues(preferenceName, values);
		}
		return Collections.unmodifiableList(preferenceValues);
	}

	private static String[] splitPreferenceValues(String preferenceName, String preferenceValue) {
		String[] values = preferenceValue.split(PREFERENCE_VALUE_SEPARATOR);
		if (values.length % 2 != 0) {
			String errorMessage = NLS.bind(
					"Expected pairs of values separated by \"{0}\" for preference \"{1}\" with value \"{2}\"", //$NON-NLS-1$
					new String[] { PREFERENCE_VALUE_SEPARATOR, preferenceName, Arrays.toString(values) });
			WorkbenchPlugin.log(new IllegalArgumentException(errorMessage));
			values = new String[0];
		}
		return values;
	}

	private static List<FileLimit> parsePreferenceValues(String preferenceName, String[] preferenceValues) {
		List<FileLimit> fileLimits = new ArrayList<>();
		for (int i = 0; i < preferenceValues.length; i += 2) {
			String sizeString = preferenceValues[i + 0];
			String editorId = preferenceValues[i + 1];
			try {
				long bytes = Long.parseLong(sizeString);
				FileLimit fileLimit = new FileLimit(editorId, bytes);
				fileLimits.add(fileLimit);
			} catch (NumberFormatException e) {
				String errorMessage = NLS.bind(
						"Skipped invalid file size value \"{0}\" stored in preference \"{1}\" with value \"{2}\"", //$NON-NLS-1$
						new String[] { sizeString, preferenceName, Arrays.toString(preferenceValues) });
				WorkbenchPlugin.log(new IllegalArgumentException(errorMessage, e));
			}
		}
		return fileLimits;
	}

	private static void setPreferenceValues(String preferenceName, List<FileLimit> fileLimits) {
		StringBuilder preferenceValue = new StringBuilder();
		for (int i = 0; i < fileLimits.size(); ++i) {
			FileLimit fileLimit = fileLimits.get(i);
			preferenceValue.append(fileLimit.fileSize);
			preferenceValue.append(PREFERENCE_VALUE_SEPARATOR);
			preferenceValue.append(fileLimit.editorId);
			if (i < fileLimits.size() - 1) {
				preferenceValue.append(PREFERENCE_VALUE_SEPARATOR);
			}
		}
		PREFERENCE_STORE.setValue(preferenceName, preferenceValue.toString());
	}

	private static String getPreferenceNameForExtension(String fileExtension) {
		String preferenceName = IPreferenceConstants.LARGE_FILE_LIMITS + EXTENSION_SEPARATOR + fileExtension;
		return preferenceName;
	}
}
