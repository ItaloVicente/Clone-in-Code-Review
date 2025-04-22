                    tool.getCommand()
                    remoteFile
			Map<String
					gitDir
                    localFile
                    remoteFile
                    null

            CommandExecutor cmdExec = new CommandExecutor(fs
            return cmdExec.run(command

        } catch (IOException | InterruptedException e) {
            throw new ToolException(e);
        } finally {
            localFile.cleanTemporaries();
            remoteFile.cleanTemporaries();
        }
    }

    public Set<String> getUserDefinedToolNames() {
        return userDefinedTools.keySet();
    }

    public Set<String> getPredefinedToolNames() {
        return predefinedTools.keySet();
    }

    public Set<String> getAllToolNames() {
        String defaultName = getDefaultToolName(false);
        if (defaultName == null) {
            defaultName = getFirstAvailableTool();
        }
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
                getPredefinedToolNames());
    }

