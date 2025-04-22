package org.eclipse.jgit.api;

import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;

public class CherryPickResult {

	public enum CherryPickStatus {
		SUCCESSFUL {
			@Override
			public String toString() {
				return "Successful";
			}
		}
		FAILED {
			public String toString() {
				return "Failed";
			}
		}
		CONFLICTING {
			public String toString() {
				return "Conflicting";
			}
		}
	}

	private CherryPickStatus status;

	private RevCommit newHead;

	private List<Ref> cherryPickedRefs;

	private Map<String

	public CherryPickResult(RevCommit newHead
		this.status = CherryPickStatus.SUCCESSFUL;
		this.newHead = newHead;
		this.cherryPickedRefs = cherryPickedRefs;
	}

	public CherryPickResult(Map<String
		this.status = CherryPickStatus.FAILED;
		this.failingPaths = failingPaths;
	}

	private CherryPickResult(CherryPickStatus status) {
		this.status = status;
	}

	public static CherryPickResult CONFLICT = new CherryPickResult(
			CherryPickStatus.CONFLICTING);

	public CherryPickStatus getStatus() {
		return status;
	}

	public RevCommit getNewHead() {
		return newHead;
	}

	public List<Ref> getCherryPickedRefs() {
		return cherryPickedRefs;
	}

	public Map<String
		return failingPaths;
	}
}
