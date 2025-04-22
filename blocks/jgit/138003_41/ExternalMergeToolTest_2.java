
package org.eclipse.jgit.pgm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.diffmergetool.ExternalMergeTool;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.diffmergetool.MergeTools;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.lib.Repository;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

@Command(name = "mergetool"
class MergeTool extends TextBuiltin {
	private MergeTools mergeTools;

	@Option(name = "--tool"
			"-t" }
	private String toolName;

	private Optional<Boolean> prompt = Optional.empty();

	@Option(name = "--prompt"
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = Optional.of(Boolean.TRUE);
	}

	@Option(name = "--no-prompt"
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = Optional.of(Boolean.FALSE);
	}

	@Option(name = "--tool-help"
	private boolean toolHelp;

	private BooleanTriState gui = BooleanTriState.UNSET;

	@Option(name = "--gui"
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanTriState.TRUE;
	}

	@Option(name = "--no-gui"
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanTriState.FALSE;
	}

	@Argument(required = false
	@Option(name = "--"
	protected List<String> filterPaths;

	@Override
	protected void init(Repository repository
		super.init(repository
		mergeTools = new MergeTools(repository);
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				boolean showPrompt = mergeTools.isInteractive();
				if (prompt.isPresent()) {
					showPrompt = prompt.get().booleanValue();
				}
				String toolNameSelected = toolName;
				if ((toolNameSelected == null) || toolNameSelected.isEmpty()) {
					toolNameSelected = mergeTools.getDefaultToolName(gui);
				}
				Map<String
				if (files.size() > 0) {
					merge(files
				} else {
				}
			}
			outw.flush();
		} catch (Exception e) {
			throw die(e.getMessage()
		}
	}

	private void merge(Map<String
			String toolNamePrompt) throws Exception {
		List<String> fileNames = new ArrayList<>(files.keySet());
		Collections.sort(fileNames);
		for (String fileName : fileNames) {
			outw.println(fileName);
		}
		outw.flush();
		for (String fileName : fileNames) {
			StageState fileState = files.get(fileName);
			if (fileState == StageState.BOTH_MODIFIED) {
				boolean launch = true;
				if (showPrompt) {
					launch = isLaunch(toolNamePrompt);
				}
				if (launch) {
				} else {
					break;
				}
			} else if ((fileState == StageState.DELETED_BY_US) || (fileState == StageState.DELETED_BY_THEM)) {
			} else {
				outw.println(
				break;
			}
		}
	}

	private boolean isLaunch(String toolNamePrompt)
			throws IOException {
		boolean launch = true;
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launch = false;
			}
		}
		return launch;
	}

	private void showToolHelp() throws IOException {
		outw.println(
		for (String name : mergeTools.getAvailableTools().keySet()) {
		}
		Map<String
				.getUserDefinedTools();
		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid
		for (String name : mergeTools.getNotAvailableTools().keySet()) {
		}
		outw.println(
				"environment. If run in a terminal-only session
		return;
	}

	private Map<String
			throws RevisionSyntaxException
			GitAPIException {
		Map<String
		try (Git git = new Git(db)) {
			StatusCommand statusCommand = git.status();
			if (filterPaths != null && filterPaths.size() > 0) {
				for (String path : filterPaths) {
					statusCommand.addPath(path);
				}
			}
			Status status = statusCommand.call();
			files = status.getConflictingStageState();
		}
		return files;
	}

}
