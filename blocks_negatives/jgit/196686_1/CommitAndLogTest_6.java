						"[master 101cffba0364877df1942891eba7f465f628a3d2] first comit", //
						"", // amend
						"[master d2169869dadf16549be20dcf8c207349d2ed6c62] first commit", //
						"", // log
						"commit d2169869dadf16549be20dcf8c207349d2ed6c62", //
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>", //
						"Date:   Sat Aug 15 20:12:58 2009 -0330", //
						"", //
						"    first commit", //
						"", //
				}, execute("git commit -m 'first comit'", //
						"git commit --amend -m 'first commit'", //
						"git log"));
