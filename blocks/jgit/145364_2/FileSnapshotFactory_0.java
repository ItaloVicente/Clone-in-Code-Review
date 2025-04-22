package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;

import org.eclipse.jgit.util.FS;

public class FileSnapshotFactory {


    public static FileSnapshotFactory ON_THE_FLY = new FileSnapshotFactory(null);

    public static FileSnapshot saveDetectTimestampResolution(File file) {
        return new FileSnapshot(file
    }


    private final Path root;

    private Duration rootTimestampResolution;


    public FileSnapshotFactory(Path root) {
        this.root = root;
    }


    public FileSnapshot save(File file) {
        final Duration timestampResolution = getTimestampResolution(file);
        return new FileSnapshot(file
    }

    Duration getTimestampResolution(File file) {
        final Duration timestampResolution;
        if (root != null && file.toPath().relativize(root) != null) {
            if (rootTimestampResolution == null) {
                rootTimestampResolution = FS.getFsTimerResolution(root);
            }

            timestampResolution = rootTimestampResolution;
        }
        else {
            timestampResolution = FS.getFsTimerResolution(file.toPath().getParent());
        }
        return timestampResolution;
    }
}
