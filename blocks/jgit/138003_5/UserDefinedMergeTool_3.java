
package org.eclipse.jgit.diffmergetool;

public enum PreDefinedMergeTools {
	araxis("compare"
	bc("bcomp"
	bc3("bcompare"
	codecompare("CodeCompare"
	deltawalker("DeltaWalker"
	diffmerge("diffmerge"
	diffuse("diffuse"
	ecmerge("ecmerge"
	emerge("emacs"
	examdiff("ExamDiff.com"
	guiffy("guiffy"
	gvimdiff("gvim"
			"-R -f -d -c 'wincmd l' -c 'cd $GIT_PREFIX' \"$LOCAL\" \"$REMOTE\""
			true)
	gvimdiff2(gvimdiff)
	gvimdiff3(gvimdiff)
	kdiff3("kdiff3"
			"--L1 \"$MERGED (A)\" --L2 \"$MERGED (B)\" \"$LOCAL\" \"$REMOTE\""
			true)
	kompare("kompare"
	meld("meld"
	opendiff("opendiff"
	p4merge("p4merge"
	tkdiff("tkdiff"
	vimdiff("vim"
			"-R -f -d -c 'wincmd l' -c 'cd $GIT_PREFIX' \"$LOCAL\" \"$REMOTE\""
			true)
	vimdiff2(vimdiff)
	vimdiff3(vimdiff)
	winmerge("WinMergeU"
	xxdiff("xxdiff"
			"-R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' \"$LOCAL\" \"$REMOTE\""
			false);

	PreDefinedMergeTools(String path
			boolean exitCodeTrustable) {
		this.path = path;
		this.parameters = parameters;
		this.exitCodeTrustable = exitCodeTrustable;
    }

	PreDefinedMergeTools(PreDefinedMergeTools from) {
		this.path = from.getPath();
		this.parameters = from.getParameters();
		this.exitCodeTrustable = from.isExitCodeTrustable();
	}

	private String path;

	private String parameters;

	private boolean exitCodeTrustable;

	public String getPath() {
		return path;
	}

	public String getParameters() {
		return parameters;
	}

	public boolean isExitCodeTrustable() {
		return exitCodeTrustable;
	}

}
