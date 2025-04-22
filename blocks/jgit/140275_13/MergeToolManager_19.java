        if (tool == null) {
            tool = predefinedTools.get(name);
        }
        return tool;
    }

    private void keepBackupFile(String mergedFilePath
            throws IOException {
        if (config.isKeepBackup()) {
            Path backupPath = backup.getFile().toPath();
            Files.move(backupPath
                    backupPath.resolveSibling(
                            Paths.get(mergedFilePath).getFileName() + ".orig")
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }
