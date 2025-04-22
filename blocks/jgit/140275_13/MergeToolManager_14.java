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
