package org.eclipse.egit.core.internal;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public interface IRepositoryObject {

	Repository getRepository();

	ObjectId getObjectId();
}
