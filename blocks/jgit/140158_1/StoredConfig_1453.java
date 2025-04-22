
package org.eclipse.jgit.lib;

import org.eclipse.jgit.internal.JGitText;
public enum RepositoryState {
	BARE {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return false; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() {
			return JGitText.get().repositoryState_bare;
		}
	}

	SAFE {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_normal; }
	}

	MERGING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return false; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_conflicts; }
	}

	MERGING_RESOLVED {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_merged; }
	}

	CHERRY_PICKING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return false; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_conflicts; }
	}

	CHERRY_PICKING_RESOLVED {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_merged; }
	}

	REVERTING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return false; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_conflicts; }
	}

	REVERTING_RESOLVED {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_merged; }
	}

	REBASING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return true; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_rebaseOrApplyMailbox; }
	}

	REBASING_REBASING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return true; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_rebase; }
	}

	APPLY {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_applyMailbox; }
	}

	REBASING_MERGE {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return true; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_rebaseWithMerge; }
	}

	REBASING_INTERACTIVE {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return true; }

		@Override
		public boolean isRebasing() { return true; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_rebaseInteractive; }
	}

	BISECTING {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return false; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public boolean isRebasing() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_bisecting; }
	};

	public abstract boolean canCheckout();

	public abstract boolean canCommit();

	public abstract boolean canResetHead();

	public abstract boolean canAmend();

	public abstract boolean isRebasing();

	public abstract String getDescription();
}
