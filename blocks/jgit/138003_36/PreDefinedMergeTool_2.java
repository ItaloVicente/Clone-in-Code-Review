
package org.eclipse.jgit.internal.diffmergetool;

import java.util.TreeMap;
import java.io.File;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class MergeTools {

	private final MergeToolConfig config;

	private final Map<String

	private final Map<String

	public MergeTools(Repository repo) {
		config = repo.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config
	}

	public ExecutionResult merge(Repository repo
			FileElement remoteFile
			String toolName
			BooleanOption gui)
			throws ToolException {
		ExternalMergeTool tool = guessTool(toolName
		try {
			File workingDir = repo.getWorkTree();
			String localFilePath = localFile.getFile().getPath();
			String remoteFilePath = remoteFile.getFile().getPath();
			String baseFilePath = baseFile.getFile().getPath();
			String command = tool.getCommand();
			command = command.replace("$LOCAL"
			command = command.replace("$REMOTE"
			command = command.replace("$MERGED"
			command = command.replace("$BASE"
			Map<String
			env.put(Constants.GIT_DIR_KEY
					repo.getDirectory().getAbsolutePath());
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			env.put("BASE"
			boolean trust = tool.getTrustExitCode().toBoolean();
			CommandExecutor cmdExec = new CommandExecutor(repo.getFS()
			return cmdExec.run(command
		} catch (Exception e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
			baseFile.cleanTemporaries();
		}
	}

	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	public Map<String
		return userDefinedTools;
	}

	public Map<String
		return predefinedTools;
	}

	public Map<String
		return new TreeMap<>();
	}

	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	public boolean isInteractive() {
		return config.isPrompt();
	}

	private ExternalMergeTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		ExternalMergeTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private ExternalMergeTool getTool(final String name) {
		ExternalMergeTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String
		Map<String
		for (CommandLineMergeTool tool : CommandLineMergeTool.values()) {
			tools.put(tool.name()
		}
		return tools;
	}

	private Map<String
			MergeToolConfig cfg
		Map<String
		Map<String
		for (String name : userTools.keySet()) {
			ExternalMergeTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
					if (userTool.getTrustExitCode().isConfigured()) {
						predefTool
								.setTrustExitCode(userTool.getTrustExitCode());
					}
				}
			}
		}
		return tools;
	}

}
