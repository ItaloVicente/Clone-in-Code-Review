            if (userTool.getCommand() != null) {
                tools.put(name
            } else if (userTool.getPath() != null) {
                PreDefinedMergeTool predefTool = (PreDefinedMergeTool) predefTools
                        .get(name);
                if (predefTool != null) {
                    predefTool.setPath(userTool.getPath());
