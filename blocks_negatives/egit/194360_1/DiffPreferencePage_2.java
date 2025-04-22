		/**
		 * Sets selected custom external diff/merge tool.
		 *
		 * @param toolId
		 *            either {@link #DIFF_TOOL_CUSTOM} or
		 *            {@link #MERGE_TOOL_CUSTOM}
		 *
		 * @param selection
		 *            tool to be set
		 *
		 */
		private void setCustomTool(String toolId, String selection) {
			toolSelections.put(toolId, selection);
		}

