
package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.blame.ReverseWalk.ReverseCommit;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.treewalk.filter.PathFilter;

class Candidate {
	Candidate queueNext;

	RevCommit sourceCommit;

	PathFilter sourcePath;

	ObjectId sourceBlob;

	RawText sourceText;

	Region regionList;

	int renameScore;

	Candidate(RevCommit commit
		sourceCommit = commit;
		sourcePath = path;
	}

	int getParentCount() {
		return sourceCommit.getParentCount();
	}

	RevCommit getParent(int idx) {
		return sourceCommit.getParent(idx);
	}

	Candidate getNextCandidate(int idx) {
		return null;
	}

	void add(RevFlag flag) {
		sourceCommit.add(flag);
	}

	int getTime() {
		return sourceCommit.getCommitTime();
	}

	PersonIdent getAuthor() {
		return sourceCommit.getAuthorIdent();
	}

	Candidate create(RevCommit commit
		return new Candidate(commit
	}

	Candidate copy(RevCommit commit) {
		Candidate r = create(commit
		r.sourceBlob = sourceBlob;
		r.sourceText = sourceText;
		r.regionList = regionList;
		return r;
	}

	void loadText(ObjectReader reader) throws IOException {
		ObjectLoader ldr = reader.open(sourceBlob
		sourceText = new RawText(ldr.getCachedBytes(Integer.MAX_VALUE));
	}

	void takeBlame(EditList editList
		blame(editList
	}

	private static void blame(EditList editList
		Region r = b.clearRegionList();
		Region aTail = null;
		Region bTail = null;

		for (int eIdx = 0; eIdx < editList.size();) {
			if (r == null)
				return;

			Edit e = editList.get(eIdx);

			if (e.getEndB() <= r.sourceStart) {
				eIdx++;
				continue;
			}

			if (r.sourceStart < e.getBeginB()) {
				int d = e.getBeginB() - r.sourceStart;
				if (r.length <= d) {
					Region next = r.next;
					r.sourceStart = e.getBeginA() - d;
					aTail = add(aTail
					r = next;
					continue;
				}

				aTail = add(aTail
				r.slideAndShrink(d);
			}


			if (e.getLengthB() == 0) {
				eIdx++;
				continue;
			}

			int rEnd = r.sourceStart + r.length;
			if (rEnd <= e.getEndB()) {
				Region next = r.next;
				bTail = add(bTail
				r = next;
				if (rEnd == e.getEndB())
					eIdx++;
				continue;
			}

			int len = e.getEndB() - r.sourceStart;
			bTail = add(bTail
			r.slideAndShrink(len);
			eIdx++;
		}

		if (r == null)
			return;

		Edit e = editList.get(editList.size() - 1);
		int endB = e.getEndB();
		int d = endB - e.getEndA();
		if (aTail == null)
			a.regionList = r;
		else
			aTail.next = r;
		do {
			if (endB <= r.sourceStart)
				r.sourceStart -= d;
			r = r.next;
		} while (r != null);
	}

	private static Region add(Region aTail
		if (aTail == null) {
			a.regionList = n;
			n.next = null;
			return n;
		}

		if (aTail.resultStart + aTail.length == n.resultStart
				&& aTail.sourceStart + aTail.length == n.sourceStart) {
			aTail.length += n.length;
			return aTail;
		}

		aTail.next = n;
		n.next = null;
		return n;
	}

	private Region clearRegionList() {
		Region r = regionList;
		regionList = null;
		return r;
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Candidate[");
		r.append(sourcePath.getPath());
		if (sourceCommit != null)
			r.append(" @ ").append(sourceCommit.abbreviate(6).name());
		if (regionList != null)
			r.append(" regions:").append(regionList);
		r.append("]");
		return r.toString();
	}

	static final class ReverseCandidate extends Candidate {
		ReverseCandidate(ReverseCommit commit
			super(commit
		}

		@Override
		int getParentCount() {
			return ((ReverseCommit) sourceCommit).getChildCount();
		}

		@Override
		RevCommit getParent(int idx) {
			return ((ReverseCommit) sourceCommit).getChild(idx);
		}

		@Override
		int getTime() {
			return -sourceCommit.getCommitTime();
		}

		@Override
		Candidate create(RevCommit commit
			return new ReverseCandidate((ReverseCommit) commit
		}

		@Override
		public String toString() {
			return "Reverse" + super.toString();
		}
	}

	static final class BlobCandidate extends Candidate {
		Candidate parent;

		String description;

		BlobCandidate(String name
			super(null
			description = name;
		}

		@Override
		int getParentCount() {
			return parent != null ? 1 : 0;
		}

		@Override
		RevCommit getParent(int idx) {
			return null;
		}

		@Override
		Candidate getNextCandidate(int idx) {
			return parent;
		}

		@Override
		void add(RevFlag flag) {
		}

		@Override
		int getTime() {
			return Integer.MAX_VALUE;
		}

		@Override
		PersonIdent getAuthor() {
			return new PersonIdent(description
		}
	}
}
