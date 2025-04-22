                PreDefinedMergeTool predefTool = (PreDefinedMergeTool) tool;
                predefTool.setAvailable(
						ExternalToolUtils.isToolAvailable(fs
                                predefTool.getPath()));
            }
        }
        return predefinedTools;
    }

    public String getFirstAvailableTool() {
        String name = null;
