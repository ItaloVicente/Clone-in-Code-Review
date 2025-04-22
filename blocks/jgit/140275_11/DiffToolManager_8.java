			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
                return tool.getName();
            }
        }

        return null;
    }

    public String getDefaultToolName(boolean gui) {
        return gui ? config.getDefaultGuiToolName()
                : config.getDefaultToolName();
    }

    public boolean isPrompt() {
        return config.isPrompt();
    }
