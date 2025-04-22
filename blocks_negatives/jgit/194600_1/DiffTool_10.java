				boolean showPrompt = diffTools.isInteractive();
				if (prompt != BooleanTriState.UNSET) {
					showPrompt = prompt == BooleanTriState.TRUE;
				}
				String toolNamePrompt = toolName;
				if (showPrompt) {
					if (StringUtils.isEmptyOrNull(toolNamePrompt)) {
						toolNamePrompt = diffTools.getDefaultToolName(gui);
					}
				}
