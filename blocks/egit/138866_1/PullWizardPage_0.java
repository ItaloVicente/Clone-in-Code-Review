package org.eclipse.egit.ui.internal.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;

public class AsynchronousBranchList extends AsynchronousListOperation<Ref> {

	private final String localBranchName;

	public AsynchronousBranchList(Repository repository, String uriText,
			String localBranchName) {
		super(repository, uriText);
		this.localBranchName = localBranchName;
	}

	@Override
	protected Collection<Ref> convert(Collection<Ref> refs) {
		List<Ref> filtered = new ArrayList<>();
		String localFullName = localBranchName != null
				? Constants.R_HEADS + localBranchName
				: null;
		boolean localBranchFound = false;
		for (Ref ref : refs) {
			String name = ref.getName();
			if (name.startsWith(Constants.R_HEADS)) {
				filtered.add(ref);
				if (localFullName != null
						&& localFullName.equalsIgnoreCase(name)) {
					localBranchFound = true;
				}
			}
		}
		Collections.sort(filtered, CommonUtils.REF_ASCENDING_COMPARATOR);
		if (localFullName != null && !localBranchFound) {
			List<Ref> newRefs = new ArrayList<>(filtered.size() + 1);
			newRefs.add(new ObjectIdRef.Unpeeled(Storage.NEW, localFullName,
					ObjectId.zeroId()));
			newRefs.addAll(filtered);
			filtered = newRefs;
		}
		return filtered;
	}

}
