package org.eclipse.jgit.api;

import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;

public class CherryPickResult {

	public enum CherryPickStatus {
		OK {
			@Override
			public String toString() {
			}
		}
		FAILED {
			@Override
			public String toString() {
			}
		}
		CONFLICTING {
			@Override
			public String toString() {
			}
		}
	}

	private final CherryPickStatus status;

	private final RevCommit newHead;

	private final List<Ref> cherryPickedRefs;

	private final Map<String

	public CherryPickResult(RevCommit newHead
		this.status = CherryPickStatus.OK;
		this.newHead = newHead;
		this.cherryPickedRefs = cherryPickedRefs;
		this.failingPaths = null;
	}

	public CherryPickResult(Map<String
		this.status = CherryPickStatus.FAILED;
		this.newHead = null;
		this.cherryPickedRefs = null;
		this.failingPaths = failingPaths;
	}

	private CherryPickResult(CherryPickStatus status) {
		this.status = status;
		this.newHead = null;
		this.cherryPickedRefs = null;
		this.failingPaths = null;
	}

	public static final CherryPickResult CONFLICT = new CherryPickResult(
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
