                    tool.getCommand(baseFile != null)
                    localFile
			Map<String
					gitDir
                    localFile
            boolean trust = tool.getTrustExitCode().toBoolean();
            CommandExecutor cmdExec = new CommandExecutor(fs
            result = cmdExec.run(command
            keepBackupFile(mergedFile.getPath()
            return result;
        } catch (IOException | InterruptedException e) {
            throw new ToolException(e);
        } finally {
            if (backup != null) {
                backup.cleanTemporaries();
            }
            if (!((result == null) && config.isKeepTemporaries())) {
                localFile.cleanTemporaries();
                remoteFile.cleanTemporaries();
                if (baseFile != null) {
                    baseFile.cleanTemporaries();
                }
                if (config.isWriteToTemp() && (tempDir != null)
                        && tempDir.exists()) {
                    tempDir.delete();
                }
            }
        }
    }

    private FileElement createBackupFile(FileElement from
            throws IOException {
        FileElement backup = new FileElement(from.getPath()
        Files.copy(from.getFile().toPath()
                backup.createTempFile(toParentDir).toPath()
                StandardCopyOption.REPLACE_EXISTING);
        return backup;
    }

    public File createTempDirectory() throws IOException {
        return config.isWriteToTemp()
                : null;
    }

    public Set<String> getUserDefinedToolNames() {
        return userDefinedTools.keySet();
    }

    public Set<String> getPredefinedToolNames() {
        return predefinedTools.keySet();
    }

    public Set<String> getAllToolNames() {
        String defaultName = getDefaultToolName(
				BooleanOption.DEFAULT_FALSE);
        if (defaultName == null) {
            defaultName = getFirstAvailableTool();
        }
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
                getPredefinedToolNames());
    }

