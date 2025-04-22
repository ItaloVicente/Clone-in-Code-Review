
package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
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

	Candidate copy(RevCommit commit) {
		Candidate r = new Candidate(commit
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
}
