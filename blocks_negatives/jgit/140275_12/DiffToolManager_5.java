			if (ExternalToolUtils.isToolAvailable(db, tool.getPath())) {
				name = tool.getName();
				break;
			}
		}
		return name;
	}
