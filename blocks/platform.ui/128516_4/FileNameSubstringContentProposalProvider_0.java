package org.eclipse.ui.internal.ide;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;

public class DirectoryProposalContentAssist {

	private class TriggerableContentProposalAdapter extends ContentProposalAdapter {

		public TriggerableContentProposalAdapter(Control control, IControlContentAdapter controlContentAdapter,
				IContentProposalProvider proposalProvider, KeyStroke keyStroke, char[] autoActivationCharacters) {
			super(control, controlContentAdapter, proposalProvider, keyStroke, autoActivationCharacters);
		}

		protected void triggerProposalPopup() {
			super.closeProposalPopup();
			super.openProposalPopup();
		}

	}

	private class AutoCompleteField {

		private SimpleContentProposalProvider proposalProvider;
		private TriggerableContentProposalAdapter adapter;

		public AutoCompleteField(Control control, IControlContentAdapter controlContentAdapter, String... proposals) {
			proposalProvider = new FileNameSubstringContentProposalProvider(proposals);
			proposalProvider.setFiltering(true);
			adapter = new TriggerableContentProposalAdapter(control, controlContentAdapter, proposalProvider, null,
					null);
			adapter.setPropagateKeys(true);
			adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		}

		public void setProposals(String... proposals) {
			proposalProvider.setProposals(proposals);
			adapter.triggerProposalPopup();
		}

	}

	private Path lastDir;
	private Job lastJob;

	public DirectoryProposalContentAssist(Combo combo) {
		AutoCompleteField autoCompleteField = new AutoCompleteField(combo, new ComboContentAdapter());

		combo.addModifyListener(e -> {
			Path dir = pathWithoutFileName(combo.getText());
			if (dir != null && dir.equals(lastDir)) {
				return;
			}
			if (dir == null || !isDirectory(dir)) {
				updateProposals(combo, autoCompleteField, new String[0]);
				lastDir = null;
				return;
			}

			lastDir = dir;
			Job job = new Job("") { //$NON-NLS-1$

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					List<String> directories = null;
					try (Stream<Path> files = Files.list(dir)) {
						directories = filterPaths(files);
					} catch (IOException ex) {
						directories = new ArrayList<>();
					}
					List<String> directoriesFinal = directories;

					String[] proposals = directoriesFinal.toArray(new String[directoriesFinal.size()]);
					updateProposals(combo, autoCompleteField, proposals);
					return Status.OK_STATUS;
				}

			};
			if (lastJob != null) {
				lastJob.cancel();
			}
			lastJob = job;
			job.schedule();
		});
	}

	private void updateProposals(Combo combo, AutoCompleteField autoCompleteField, String[] proposals) {
		combo.getDisplay().syncExec(() -> {
			autoCompleteField.setProposals(proposals);
		});
	}

	private Path pathWithoutFileName(String inputPath) {
		int lastIndex = inputPath.lastIndexOf(File.separatorChar);
		if (separatorNotFound(lastIndex)) {
			return null;
		}
		return getPath(removeFileName(inputPath, lastIndex));
	}

	private boolean separatorNotFound(int lastIndex) {
		return lastIndex < 0;
	}

	private String removeFileName(String text, int lastIndex) {
		if (lastIndex == 0) {
			return File.separator;
		}
		return text.substring(0, lastIndex);
	}

	private Path getPath(String text) {
		try {
			return Paths.get(text);
		} catch (InvalidPathException ex) {
			return null;
		}
	}

	private boolean isDirectory(Path dir) {
		try {
			return Files.isDirectory(dir);
		} catch (SecurityException ex) {
			return false;
		}
	}

	private List<String> filterPaths(Stream<Path> paths) {
		return paths.filter(path -> {
			String[] directoriesInPath = path.toString().split(File.separator);
			String fileName = directoriesInPath[directoriesInPath.length - 1];
			String lastDirectory = directoriesInPath[directoriesInPath.length - 2];
			return !lastDirectory.equals(".") && !fileName.startsWith(".") && Files.isDirectory(path); //$NON-NLS-1$//$NON-NLS-2$
		}).map(Path::toString).collect(Collectors.toList());
	}

}
