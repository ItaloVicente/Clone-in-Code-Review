package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class CompareWithHeadCommand extends CompareWithCommand {

	@Override
	protected String getRef(ExecutionEvent event, @NonNull Repository repository,
			Collection<IPath> paths) {
		return Constants.HEAD;
	}
}
