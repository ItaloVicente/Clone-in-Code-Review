package org.eclipse.e4.core.macros.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.macros.Activator;
import org.eclipse.e4.core.macros.EMacroContext;
import org.eclipse.e4.core.macros.IMacroCommand;
import org.eclipse.e4.core.macros.IMacroCommandFactory;
import org.eclipse.e4.core.macros.IMacroContextListener;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;

public class MacroManager {

	private static final String JS_EXT = ".js"; //$NON-NLS-1$

	private static final String TEMP_MACRO_PREFIX = "temp_macro_"; //$NON-NLS-1$

	private int fMaxNumberOfTemporaryMacros = 5;

	public void setmaxNumberOfTemporaryMacros(int maxNumberOfTemporaryMacros) {
		Assert.isTrue(maxNumberOfTemporaryMacros >= 1);
		this.fMaxNumberOfTemporaryMacros = maxNumberOfTemporaryMacros;
	}

	public int getMaxNumberOfTemporaryMacros() {
		return fMaxNumberOfTemporaryMacros;
	}

	private File[] fMacrosDirectories;

	private ComposableMacro fMacroBeingRecorded;

	private IMacro fLastMacro;

	private boolean fIsPlayingBack = false;

	public MacroManager(File... macrosDirectories) {
		setMacrosDirectories(macrosDirectories);
	}

	public void setMacrosDirectories(File... macrosDirectories) {
		Assert.isNotNull(macrosDirectories);
		for (File file : macrosDirectories) {
			Assert.isNotNull(file);
		}
		this.fMacrosDirectories = macrosDirectories;
		reloadMacros();
	}

	public boolean isRecording() {
		return fMacroBeingRecorded != null;
	}

	public boolean isPlayingBack() {
		return fIsPlayingBack;
	}

	public void addMacroCommand(IMacroCommand macroCommand) {
		ComposableMacro macroBeingRecorded = fMacroBeingRecorded;
		if (macroBeingRecorded != null) {
			macroBeingRecorded.addMacroCommand(macroCommand);
		}
	}

	public void toggleMacroRecord(final EMacroContext macroContext,
			Map<String, IMacroCommandFactory> commandIdToFactory) {
		if (fIsPlayingBack) {
			return;
		}
		try {
			if (fMacroBeingRecorded == null) {
				fMacroBeingRecorded = new ComposableMacro(commandIdToFactory);
			} else {
				try {
					saveTemporaryMacro(fMacroBeingRecorded);
				} finally {
					fLastMacro = fMacroBeingRecorded;
					fMacroBeingRecorded = null;
				}
			}
		} finally {
			notifyMacroStateChange(macroContext);
		}

	}

	private static final class PathAndTime {

		private Path fPath;
		private long fLastModified;

		public PathAndTime(Path path, long lastModified) {
			this.fPath = path;
			this.fLastModified = lastModified;
		}

	}

	private void saveTemporaryMacro(ComposableMacro macro) {
		if (fMacrosDirectories == null || this.fMacrosDirectories.length == 0) {
			return;
		}
		File macroDirectory = this.fMacrosDirectories[0];
		if (!macroDirectory.isDirectory()) {
			return;
		}

		List<PathAndTime> pathAndTime = listTemporaryMacrosPathAndTime(macroDirectory);

		try {
			Path tempFile = Files.createTempFile(Paths.get(macroDirectory.toURI()), TEMP_MACRO_PREFIX, JS_EXT);
			Files.write(tempFile, macro.toJSBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e1) {
			Activator.log(e1);
			return; // Can't create file at expected place;
		}

		while (pathAndTime.size() >= fMaxNumberOfTemporaryMacros) {
			PathAndTime removeFile = pathAndTime.remove(pathAndTime.size() - 1);
			try {
				Files.deleteIfExists(removeFile.fPath);
			} catch (Exception e) {
				Activator.log(e);
			}
		}
	}

	private List<PathAndTime> listTemporaryMacrosPathAndTime(File macroDirectory) {
		List<PathAndTime> pathAndTime = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(macroDirectory.toURI()),
				new DirectoryStream.Filter<Path>() {

					@Override
					public boolean accept(Path entry) {
						String name = entry.getFileName().toString().toLowerCase();
						return name.startsWith(TEMP_MACRO_PREFIX) && name.endsWith(JS_EXT);
					}
				})) {
			for (Path p : directoryStream) {
				pathAndTime.add(new PathAndTime(p, Files.getLastModifiedTime(p).to(TimeUnit.NANOSECONDS)));
			}
		} catch (IOException e1) {
			Activator.log(e1);
		}

		Collections.sort(pathAndTime, new Comparator<PathAndTime>() {

			@Override
			public int compare(PathAndTime o1, PathAndTime o2) {
				return Long.compare(o2.fLastModified, o1.fLastModified);
			}
		});
		return pathAndTime;
	}

	private void notifyMacroStateChange(final EMacroContext macroContext) {
		for (final IMacroContextListener listener : fListeners) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.macroStateChanged(macroContext);
				}

				@Override
				public void handleException(Throwable exception) {
					Activator.log(exception);
				}
			});
		}
	}

	public void playbackLastMacro(EMacroContext macroContext, final IMacroPlaybackContext macroPlaybackContext,
			final Map<String, IMacroCommandFactory> commandIdToFactory) {
		if (fLastMacro != null && !fIsPlayingBack) {
			fIsPlayingBack = true;
			try {
				notifyMacroStateChange(macroContext);
				SafeRunner.run(new ISafeRunnable() {

					@Override
					public void run() throws Exception {
						fLastMacro.setCommandIdToFactory(commandIdToFactory);
						fLastMacro.playback(macroPlaybackContext);
					}

					@Override
					public void handleException(Throwable exception) {
						Activator.log(exception);
					}
				});
			} finally {
				fIsPlayingBack = false;
			}
		}
	}

	private final ListenerList<IMacroContextListener> fListeners = new ListenerList<>();

	public void addMacroListener(IMacroContextListener listener) {
		fListeners.add(listener);
	}

	public void removeMacroListener(IMacroContextListener listener) {
		fListeners.remove(listener);
	}

	public IMacroContextListener[] getMacroListeners() {
		Object[] listeners = fListeners.getListeners();
		IMacroContextListener[] macroContextListeners = new IMacroContextListener[listeners.length];
		System.arraycopy(listeners, 0, macroContextListeners, 0, listeners.length);
		return macroContextListeners;
	}

	public void reloadMacros() {
		for (File macroDirectory : this.fMacrosDirectories) {
			if (macroDirectory.isDirectory()) {
				List<PathAndTime> listPathsAndTimes = listTemporaryMacrosPathAndTime(macroDirectory);
				if (listPathsAndTimes.size() > 0) {
					this.fLastMacro = new SavedJSMacro(listPathsAndTimes.get(0).fPath.toFile());
				}
			} else {
				Activator.log(new RuntimeException(String.format("Expected: %s to be a directory.", macroDirectory))); //$NON-NLS-1$
			}
		}
	}
}
