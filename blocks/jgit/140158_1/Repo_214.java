package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.RemoteRemoveCommand;
import org.eclipse.jgit.api.RemoteSetUrlCommand;
import org.eclipse.jgit.api.RemoteSetUrlCommand.UriType;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = false
class Remote extends TextBuiltin {

	@Option(name = "--verbose"
	private boolean verbose = false;

	@Option(name = "--prune"
			"-p" }
	private boolean prune;

	@Option(name = "--push"
	private boolean push;

	@Argument(index = 0
	private String command;

	@Argument(index = 1
	private String name;

	@Argument(index = 2
	private String uri;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			if (null == command) {
				RemoteListCommand cmd = git.remoteList();
				List<RemoteConfig> remotes = cmd.call();
				print(remotes);
			} else switch (command) {
                        case "add":
                            {
                                RemoteAddCommand cmd = git.remoteAdd();
                                cmd.setName(name);
                                cmd.setUri(new URIish(uri));
                                cmd.call();
                                break;
                            }
                        case "remove":
                        case "rm":
                            {
                                RemoteRemoveCommand cmd = git.remoteRemove();
                                cmd.setRemoteName(name);
                                cmd.call();
                                break;
                            }
                        case "set-url":
                            {
                                RemoteSetUrlCommand cmd = git.remoteSetUrl();
                                cmd.setRemoteName(name);
                                cmd.setRemoteUri(new URIish(uri));
                                cmd.setUriType(push ? UriType.PUSH : UriType.FETCH);
                                cmd.call();
                                break;
                            }
                        case "update":
                            Fetch fetch = new Fetch();
                            fetch.init(db
                            StringWriter osw = new StringWriter();
                            fetch.outw = new ThrowingPrintWriter(osw);
                            StringWriter esw = new StringWriter();
                            fetch.errw = new ThrowingPrintWriter(esw);
                            List<String> fetchArgs = new ArrayList<>();
                            if (verbose) {
                            }
                            if (prune) {
                            }
                            if (name != null) {
                                fetchArgs.add(name);
                            }
                            fetch.execute(fetchArgs.toArray(new String[0]));
                            fetch.outw.flush();
                            fetch.errw.flush();
                            outw.println(osw.toString());
                            errw.println(esw.toString());
                            break;
                        default:
                            throw new JGitInternalException(MessageFormat
                                    .format(CLIText.get().unknownSubcommand
                    }
		} catch (Exception e) {
			throw die(e.getMessage()
		}
	}

	@Override
	public void printUsage(String message
			throws IOException {
		errw.println(message);
		errw.println(

		errw.println();
		clp.printUsage(errw
		errw.println();

		errw.flush();
	}

	private void print(List<RemoteConfig> remotes) throws IOException {
		for (RemoteConfig remote : remotes) {
			String remoteName = remote.getName();
			if (verbose) {
				List<URIish> fetchURIs = remote.getURIs();
				List<URIish> pushURIs = remote.getPushURIs();

				if (!fetchURIs.isEmpty()) {
					fetchURI = fetchURIs.get(0).toString();
				} else if (!pushURIs.isEmpty()) {
					fetchURI = pushURIs.get(0).toString();
				}

				if (!pushURIs.isEmpty()) {
					pushURI = pushURIs.get(0).toString();
				} else if (!fetchURIs.isEmpty()) {
					pushURI = fetchURIs.get(0).toString();
				}

				outw.println(
						String.format("%s\t%s (fetch)"
				outw.println(
						String.format("%s\t%s (push)"
			} else {
				outw.println(remoteName);
			}
		}
	}

}
