				boolean showPrompt = mergeTools.isInteractive();
				if (prompt != BooleanTriState.UNSET) {
					showPrompt = prompt == BooleanTriState.TRUE;
				}
				String toolNameSelected = toolName;
				if ((toolNameSelected == null) || toolNameSelected.isEmpty()) {
					toolNameSelected = mergeTools.getDefaultToolName(gui);
				}
