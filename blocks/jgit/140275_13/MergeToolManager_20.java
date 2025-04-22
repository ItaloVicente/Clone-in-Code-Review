        for (PreDefinedMergeTools tool : PreDefinedMergeTools.values()) {
            tools
                    .put(tool.name()
                            new PreDefinedMergeTool(tool.name()
                                    tool.getParameters(true)
                                    tool.getParameters(false)
