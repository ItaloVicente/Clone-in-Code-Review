package org.eclipse.egit.ui;

public interface IBranchNameProvider extends ICommitMessageProvider {

	public String getBranchNameSuggestion();

	public String getDefaultSourceReference();

}
