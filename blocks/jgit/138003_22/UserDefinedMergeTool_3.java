
package org.eclipse.jgit.diffmergetool;

@SuppressWarnings("nls")
public enum PreDefinedMergeTools {
	araxis("compare"
			"-wait -merge -3 -a1 \"$BASE\" \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
			"-wait -2 \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
			false)
	bc("bcomp"
			"\"$LOCAL\" \"$REMOTE\" --mergeoutput=\"$MERGED\""
			false)
	bc3("bcompare"
	codecompare("CodeMerge"
			"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -BF=\"$BASE\" -RF=\"$MERGED\""
			"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -RF=\"$MERGED\""
			false)
	deltawalker("DeltaWalker"
			"\"$LOCAL\" \"$REMOTE\" \"$BASE\" -pwd=\"$(pwd)\" -merged=\"$MERGED\""
			"\"$LOCAL\" \"$REMOTE\" -pwd=\"$(pwd)\" -merged=\"$MERGED\""
			true)
	diffmerge("diffmerge"
			"--merge --result=\"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\""
			"--merge --result=\"$MERGED\" \"$LOCAL\" \"$REMOTE\""
			true)
	diffuse("diffuse"
			"\"$LOCAL\" \"$MERGED\" \"$REMOTE\""
	ecmerge("ecmerge"
			"--default --mode=merge3 \"$BASE\" \"$LOCAL\" \"$REMOTE\" --to=\"$MERGED\""
			"--default --mode=merge2 \"$LOCAL\" \"$REMOTE\" --to=\"$MERGED\""
			false)
	emerge("emacs"
			"-f emerge-files-with-ancestor-command \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$(basename \"$MERGED\")\""
			"-f emerge-files-command \"$LOCAL\" \"$REMOTE\" \"$(basename \"$MERGED\")\""
			true)
	examdiff("ExamDiff"
			"-merge \"$LOCAL\" \"$BASE\" \"$REMOTE\" -o:\"$MERGED\" -nh"
			"-merge \"$LOCAL\" \"$REMOTE\" -o:\"$MERGED\" -nh"
			false)
	guiffy("guiffy"
			"-m \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
	gvimdiff("gvim"
			"-f -d -c '4wincmd w | wincmd J' \"$LOCAL\" \"$BASE\" \"$REMOTE\" \"$MERGED\""
			"-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\""
			true)
	gvimdiff2("gvim"
			"-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\""
	gvimdiff3("gvim"
			"-f -d -c 'hid | hid | hid' \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$MERGED\""
			"-f -d -c 'hid | hid' \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
	kdiff3("kdiff3"
			"--auto --L1 \"$MERGED (Base)\" --L2 \"$MERGED (Local)\" --L3 \"$MERGED (Remote)\" -o \"$MERGED\" \"$BASE\" \"$LOCAL\" \"$REMOTE\""
			"--auto --L1 \"$MERGED (Local)\" --L2 \"$MERGED (Remote)\" -o \"$MERGED\" \"$LOCAL\" \"$REMOTE\""
			true)
	meld("meld"
			"\"$LOCAL\" \"$MERGED\" \"$REMOTE\""
			false)
	opendiff("opendiff"
			"\"$LOCAL\" \"$REMOTE\" -ancestor \"$BASE\" -merge \"$MERGED\""
			"\"$LOCAL\" \"$REMOTE\" -merge \"$MERGED\""
			false)
	p4merge("p4merge"
			"\"$REMOTE\" \"$LOCAL\" \"$MERGED\""
	tkdiff("tkdiff"
			"-o \"$MERGED\" \"$LOCAL\" \"$REMOTE\""
			true)
	tortoisemerge("tortoisemerge"
			"-base:\"$BASE\" -mine:\"$LOCAL\" -theirs:\"$REMOTE\" -merged:\"$MERGED\""
			null
			false)
	tortoisegitmerge("tortoisegitmerge"
			"-base \"$BASE\" -mine \"$LOCAL\" -theirs \"$REMOTE\" -merged \"$MERGED\""
			null
	vimdiff("vim"
	vimdiff2("vim"
	vimdiff3("vim"
	winmerge("WinMergeU"
			"-u -e -dl Local -dr Remote \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
			"-u -e -dl Local -dr Remote \"$LOCAL\" \"$REMOTE\" \"$MERGED\""
			false)
	xxdiff("xxdiff"
			"-X --show-merged-pane -R 'Accel.SaveAsMerged: \"Ctrl+S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\""
			"-X -R 'Accel.SaveAsMerged: \"Ctrl+S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl+G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$REMOTE\""
			false);

	PreDefinedMergeTools(String path
			String parametersWithoutBase
			boolean exitCodeTrustable) {
		this.path = path;
		this.parametersWithBase = parametersWithBase;
		this.parametersWithoutBase = parametersWithoutBase;
		this.exitCodeTrustable = exitCodeTrustable;
    }

	PreDefinedMergeTools(PreDefinedMergeTools from) {
		this(from.getPath()
				from.getParameters(false)
	}

	PreDefinedMergeTools(String path
		this(path
				from.isExitCodeTrustable());
	}

	private String path;

	private String parametersWithBase;

	private String parametersWithoutBase;

	private boolean exitCodeTrustable;

	public String getPath() {
		return path;
	}

	public String getParameters(boolean withBase) {
		if (withBase) {
			return parametersWithBase;
		}
		return parametersWithoutBase;
	}

	public boolean isExitCodeTrustable() {
		return exitCodeTrustable;
	}

	public boolean canMergeWithoutBasePresent() {
		return parametersWithoutBase != null;
	}

}
