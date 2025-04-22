			String toolPath = config.getString(sectionName, toolName,
					"path"); //$NON-NLS-1$
			if (toolPath != null && !toolPath.equals("")) { //$NON-NLS-1$
				manager.addUserOverloadedTool(toolName, toolPath);
			} else {
				String toolCmd = config.getString(sectionName,
						toolName, "cmd"); //$NON-NLS-1$
				if (toolCmd != null && !toolCmd.equals("")) { //$NON-NLS-1$
					manager.addUserDefinedTool(toolName, toolCmd);
				}
			}
