

package org.eclipse.jgit.niofs.internal.hook;

import java.util.HashMap;
import java.util.Map;

public class FileSystemHookExecutionContext {

    private String fsName;

    private Map<String

    public FileSystemHookExecutionContext(String fsName) {
        this.fsName = fsName;
    }

    public String getFsName() {
        return fsName;
    }

    public void addParam(String name
        params.put(name
    }

    public Object getParamValue(String name) {
        return params.get(name);
    }
}
