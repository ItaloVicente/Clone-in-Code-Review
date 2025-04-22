package org.eclipse.ui.internal.preferences.esnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.swt.widgets.Shell;

public class EclipseSnapshotPreferencesExporter extends AbstractEclipseSnapshotPrefImpExp {

	public boolean exportEpf(String dest, Shell shell) {
		File exportFile = new File(dest);
		FileOutputStream fileOutputStream = null;
		try {
			try {
				fileOutputStream = new FileOutputStream(exportFile);
			} catch (FileNotFoundException e) {
				reportError(shell, e);
				return false;
			}
			IPreferencesService service = Platform.getPreferencesService();
			try {
				service.exportPreferences(service.getRootNode(), getSetAllFilter(), fileOutputStream);
			} catch (CoreException e) {
				reportError(shell, e);
				return false;
			}
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					reportError(shell, e);
					return false;
				}
			}
		}
		return true;
	}
}
