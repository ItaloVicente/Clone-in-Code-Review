
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.ui.model.fragment.MModelFragment;
import org.eclipse.e4.ui.model.fragment.MModelFragments;

public final class ModelFragmentWrapper {
	private MModelFragment modelFragment;
	private MModelFragments fragmentContainer;
	private boolean checkExists;
	private String contributorName;
	private String contributorURI;

	public ModelFragmentWrapper(MModelFragments fragmentContainer, MModelFragment modelFragment, String contributorName,
			String contributorURI, boolean checkExists) {
		super();
		this.modelFragment = modelFragment;
		this.fragmentContainer = fragmentContainer;
		this.checkExists = checkExists;
		this.contributorName = contributorName;
		this.contributorURI = contributorURI;
	}

	public MModelFragment getModelFragment() {
		return modelFragment;
	}

	public void setModelFragment(MModelFragment modelFragment) {
		this.modelFragment = modelFragment;
	}

	public MModelFragments getFragmentContainer() {
		return fragmentContainer;
	}

	public void setFragmentContainer(MModelFragments fragmentContainer) {
		this.fragmentContainer = fragmentContainer;
	}

	public boolean isCheckExists() {
		return checkExists;
	}

	public void setCheckExists(boolean checkExists) {
		this.checkExists = checkExists;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public String getContributorURI() {
		return contributorURI;
	}

	public void setContributorURI(String contributorURI) {
		this.contributorURI = contributorURI;
	}
}
