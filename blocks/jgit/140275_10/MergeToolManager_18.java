			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
                name = tool.getName();
                break;
            }
        }
        return name;
    }

    public String getDefaultToolName(BooleanOption gui) {
        return gui.toBoolean() ? config.getDefaultGuiToolName()
                : config.getDefaultToolName();
    }

    public boolean isPrompt() {
        return config.isPrompt();
    }

    public String getDefaultToolName(boolean gui) {
        return gui ? config.getDefaultGuiToolName()
                : config.getDefaultToolName();
    }
