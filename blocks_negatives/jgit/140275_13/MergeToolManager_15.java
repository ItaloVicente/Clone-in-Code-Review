				PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
				predefTool.setAvailable(
						ExternalToolUtils.isToolAvailable(db,
								predefTool.getPath()));
			}
		}
		return predefinedTools;
	}

	/**
	 * @return the name of first available predefined tool or null
	 */
	public String getFirstAvailableTool() {
		String name = null;
