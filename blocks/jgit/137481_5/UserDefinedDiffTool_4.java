
package org.eclipse.jgit.diffmergetool;

public enum PreDefinedDiffTools {
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
			"-R -f -d -c 'wincmd l' -c 'cd $GIT_PREFIX' \"$LOCAL\" \"$REMOTE\"")
	gvimdiff2(gvimdiff)
	gvimdiff3(gvimdiff)
	kdiff3("kdiff3"
			"--L1 \"$MERGED (A)\" --L2 \"$MERGED (B)\" \"$LOCAL\" \"$REMOTE\"")
	kompare("kompare"
	meld("meld"
	opendiff("opendiff"
	p4merge("p4merge"
	tkdiff("tkdiff"
	vimdiff("vim"
			"-R -f -d -c 'wincmd l' -c 'cd $GIT_PREFIX' \"$LOCAL\" \"$REMOTE\"")
	vimdiff2(vimdiff)
	vimdiff3(vimdiff)
	winmerge("WinMergeU"
	xxdiff("xxdiff"

	PreDefinedDiffTools(String path
		this.path = path;
		this.parameters = parameters;
    }

	PreDefinedDiffTools(PreDefinedDiffTools from) {
		this.path = from.getPath();
		this.parameters = from.getParameters();
	}

	private String path;

	private String parameters;

	public String getPath() {
		return path;
	}

	public String getParameters() {
		return parameters;
	}

}
