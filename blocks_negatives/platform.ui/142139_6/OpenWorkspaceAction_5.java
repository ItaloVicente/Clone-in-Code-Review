	/**
	 * Create and return a string with command line options for eclipse.exe that
	 * will launch a new workbench that is the same as the currently running
	 * one, but using the argument directory as its workspace.
	 *
	 * @param workspace
	 *            the directory to use as the new workspace
	 * @return a string of command line options or null on error
	 */
	private String buildCommandLine(String workspace) {
		String property = System.getProperty(PROP_VM);
		if (property == null) {
			MessageDialog.openError(window.getShell(), IDEWorkbenchMessages.OpenWorkspaceAction_errorTitle,
					NLS.bind(IDEWorkbenchMessages.OpenWorkspaceAction_errorMessage, PROP_VM));
			return null;
		}

		StringBuilder result = new StringBuilder(512);
		result.append(property);
		result.append(NEW_LINE);

		String vmargs = System.getProperty(PROP_VMARGS);
		if (vmargs != null) {
			result.append(vmargs);
		}

		property = System.getProperty(PROP_COMMANDS);
		if (property == null) {
			result.append(CMD_DATA);
			result.append(NEW_LINE);
			result.append(workspace);
			result.append(NEW_LINE);
		} else {
			int cmd_data_pos = property.lastIndexOf(CMD_DATA);
			if (cmd_data_pos != -1) {
				cmd_data_pos += CMD_DATA.length() + 1;
				result.append(property.substring(0, cmd_data_pos));
				result.append(workspace);
				int nextArg = property.indexOf("\n-", cmd_data_pos - 1); //$NON-NLS-1$
				if (nextArg != -1) {
					result.append(property.substring(nextArg));
				}
			} else {
				result.append(CMD_DATA);
				result.append(NEW_LINE);
				result.append(workspace);
				result.append(NEW_LINE);
				result.append(property);
			}
		}

		if (vmargs != null) {
			if (result.charAt(result.length() - 1) != '\n') {
				result.append('\n');
			}
			result.append(CMD_VMARGS);
			result.append(NEW_LINE);
			result.append(vmargs);
		}

		return result.toString();
	}

