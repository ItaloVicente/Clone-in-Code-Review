package org.eclipse.jgit.api;

public class SubmoduleDeinitResult {
    private String path;
    private SubmoduleDeinitCommand.SubmoduleDeinitStatus status;

    public SubmoduleDeinitResult(String path
        this.path = path;
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SubmoduleDeinitCommand.SubmoduleDeinitStatus getStatus() {
        return status;
    }

    public void setStatus(SubmoduleDeinitCommand.SubmoduleDeinitStatus status) {
        this.status = status;
    }
}
