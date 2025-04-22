        if (SystemReader.getInstance().isWindows()) {
            destHookFile.setReadable(true);
            destHookFile.setWritable(true);
            destHookFile.setExecutable(true);
        } else {
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
