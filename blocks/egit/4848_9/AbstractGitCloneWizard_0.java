package org.eclipse.egit.core.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.jgit.lib.Repository;

public class SetRepositoryConfigPropertyTask implements PostCloneTask {

	private final String section;
	private final String subsection;
	private final String name;
	private final String value;

	public SetRepositoryConfigPropertyTask(String section, String subsection, String name, String value) {
		this.section = section;
		this.subsection = subsection;
		this.name = name;
		this.value = value;
	}

	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		try {
			repository.getConfig().setString(section, subsection, name, value);
			repository.getConfig().save();
		} catch (IOException e) {
			throw new CoreException(Activator.error(e.getMessage(), e));
		}
	}

}
