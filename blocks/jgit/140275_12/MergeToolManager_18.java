			if (ExternalToolUtils.isToolAvailable(fs
					tool.getPath())) {
                name = tool.getName();
                break;
            }
        }
        return name;
    }
