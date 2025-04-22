package org.eclipse.jgit.hooks;

import java.io.PrintStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.LfsFactory;

public class Hooks {

	public static PreCommitHook preCommit(Repository repo
			PrintStream outputStream) {
		return new PreCommitHook(repo
	}

	public static PostCommitHook postCommit(Repository repo
			PrintStream outputStream) {
		return new PostCommitHook(repo
	}

	public static CommitMsgHook commitMsg(Repository repo
			PrintStream outputStream) {
		return new CommitMsgHook(repo
	}

	public static PrePushHook prePush(Repository repo
		if (LfsFactory.getInstance().isAvailable()) {
			PrePushHook hook = LfsFactory.getInstance().getPrePushHook(repo
					outputStream);
			if (hook != null) {
				if (hook.isNativeHookPresent()) {
					PrintStream ps = outputStream;
					if (ps == null) {
						ps = System.out;
					}
					ps.println(MessageFormat
							.format(JGitText.get().lfsHookConflict
				}
				return hook;
			}
		}
		return new PrePushHook(repo
	}
}
