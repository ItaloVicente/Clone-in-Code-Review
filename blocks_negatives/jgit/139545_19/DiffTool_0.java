				String toolNamePrompt = toolName;
				if (showPrompt) {
					if (StringUtils.isEmptyOrNull(toolNamePrompt)) {
						toolNamePrompt = diffTools.getDefaultToolName(gui);
					}
				}
