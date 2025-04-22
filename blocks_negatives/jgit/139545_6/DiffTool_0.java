				String toolNamePrompt = toolName;
				if (showPrompt) {
					if ((toolNamePrompt == null) || toolNamePrompt.isEmpty()) {
						toolNamePrompt = diffToolMgr.getDefaultToolName(gui);
					}
				}
