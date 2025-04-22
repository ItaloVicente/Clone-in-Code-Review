            if (userTool.getCommand() != null) {
                tools.put(name
            } else if (userTool.getPath() != null) {
                PreDefinedDiffTool predefTool = (PreDefinedDiffTool) predefTools
                        .get(name);
                if (predefTool != null) {
                    predefTool.setPath(userTool.getPath());
                }
            }
        }
        return tools;
    }
