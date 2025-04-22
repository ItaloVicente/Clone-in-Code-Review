
package org.eclipse.jgit.niofs.internal.daemon.git;

import java.io.IOException;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public abstract class DaemonService {

    private final String command;

    private final SectionParser<ServiceConfig> configKey;

    private boolean enabled;

    private boolean overridable;

    DaemonService(final String cmdName
                  final String cfgName) {
        command = cmdName.startsWith("git-") ? cmdName : "git-" + cmdName;
        configKey = cfg -> new ServiceConfig(DaemonService.this
                                             cfg
                                             cfgName);
        overridable = true;
    }

    private static class ServiceConfig {

        final boolean enabled;

        ServiceConfig(final DaemonService service
                      final Config cfg
                      final String name) {
            enabled = cfg.getBoolean("daemon"
                                     name
                                     service.isEnabled());
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean on) {
        enabled = on;
    }

    public boolean isOverridable() {
        return overridable;
    }

    public void setOverridable(final boolean on) {
        overridable = on;
    }

    public String getCommandName() {
        return command;
    }

    public boolean handles(final String commandLine) {
        return command.length() + 1 < commandLine.length()
                && commandLine.charAt(command.length()) == ' '
                && commandLine.startsWith(command);
    }

    void execute(final org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client
                 final String commandLine)
            throws IOException
            ServiceNotAuthorizedException {
        final String name = commandLine.substring(command.length() + 1);
        Repository db;
        try {
            db = client.getDaemon().openRepository(client
                                                   name);
        } catch (ServiceMayNotContinueException e) {
            PacketLineOut pktOut = new PacketLineOut(client.getOutputStream());
            pktOut.writeString("ERR " + e.getMessage() + "\n");
            db = null;
        }
        if (db == null) {
            return;
        }
        try {
            if (isEnabledFor(db)) {
                execute(client
                        db);
            }
        } finally {
            db.close();
        }
    }

    private boolean isEnabledFor(final Repository db) {
        if (isOverridable()) {
            return db.getConfig().get(configKey).enabled;
        }
        return isEnabled();
    }

    abstract void execute(org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client
                          Repository db)
            throws IOException
            ServiceNotAuthorizedException;
}
