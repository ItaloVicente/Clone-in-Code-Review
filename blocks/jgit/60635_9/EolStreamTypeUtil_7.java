package org.eclipse.jgit.util.io;

import org.eclipse.jgit.lib.CoreConfig.EolStreamType;

public interface EolStreamTypeProvider {

	public EolStreamType getEolStreamType();

}
