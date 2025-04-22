
package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.io.File;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class MergeToolManager {

	private final MergeToolConfig config;

	private final Map<String

	private final Map<String

	public MergeToolManager(Repository db) {
		config = db.getConfig().get(MergeToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config
	}

	public ExecutionResult merge(Repository db
			FileElement remoteFile
			String toolName
			BooleanOption gui)
			throws ToolException {
		IMergeTool tool = guessTool(toolName
		try {
			File workingDir = db.getWorkTree();
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
			env.put("LOCAL"
			env.put("REMOTE"
			env.put("MERGED"
			env.put("BASE"
			boolean trust = tool.getTrustExitCode().toBoolean();
			CommandExecutor cmdExec = new CommandExecutor(db.getFS()
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

	public boolean isPrompt() {
		return config.isPrompt();
	}

	private IMergeTool guessTool(String toolName
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		IMergeTool tool = getTool(toolName);
		if (tool == null) {
		}
		return tool;
	}

	private IMergeTool getTool(final String name) {
		IMergeTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String
		Map<String
		for (PreDefinedMergeTools tool : PreDefinedMergeTools.values()) {
			tools
					.put(tool.name()
							new PreDefinedMergeTool(tool.name()
									tool.getParameters(true)
									tool.getParameters(false)
									BooleanOption.defined(
											tool.isExitCodeTrustable())));
		}
		return tools;
	}

	private Map<String
			Map<String
		Map<String
		Map<String
		for (String name : userTools.keySet()) {
			IMergeTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name
			} else if (userTool.getPath() != null) {
				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
					if (userTool.getTrustExitCode().isDefined()) {
						predefTool
								.setTrustExitCode(userTool.getTrustExitCode());
					}
				}
			}
		}
		return tools;
	}

}
