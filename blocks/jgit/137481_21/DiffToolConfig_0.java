
package org.eclipse.jgit.diffmergetool;


@SuppressWarnings("nls")
public enum CommandLineDiffTool {
	araxis("compare"
	bc("bcomp"
	bc3("bcompare"
	codecompare("CodeCompare"
	deltawalker("DeltaWalker"
	diffmerge("diffmerge"
	diffuse("diffuse"
	ecmerge("ecmerge"
	emerge("emacs"
	examdiff("ExamDiff"
	guiffy("guiffy"
	gvimdiff("gviewdiff"
	gvimdiff2(gvimdiff)
	gvimdiff3(gvimdiff)
	kdiff3("kdiff3"
			"--L1 \"$MERGED (A)\" --L2 \"$MERGED (B)\" \"$LOCAL\" \"$REMOTE\"")
	kompare("kompare"
	meld("meld"
	opendiff("opendiff"
	p4merge("p4merge"
	tkdiff("tkdiff"
	vimdiff("viewdiff"
	vimdiff2(vimdiff)
	vimdiff3(vimdiff)
	winmerge("WinMergeU"
	xxdiff("xxdiff"
			"-R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' \"$LOCAL\" \"$REMOTE\"");

	CommandLineDiffTool(String path
		this.path = path;
		this.parameters = parameters;
    }

	CommandLineDiffTool(CommandLineDiffTool from) {
		this.path = from.getPath();
		this.parameters = from.getParameters();
	}

	CommandLineDiffTool(String path
		this.path = path;
		this.parameters = from.getParameters();
	}

	private final String path;

	private final String parameters;

	public String getPath() {
		return path;
	}

	public String getParameters() {
		return parameters;
	}

}
