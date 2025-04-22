
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;

@Command(usage = "usage_RevList")
class RevList extends RevWalkTextBuiltin {
	@Override
	protected void show(RevCommit c) throws Exception {
		if (c.has(RevFlag.UNINTERESTING))
			outw.print('-');
		c.getId().copyTo(outbuffer
		if (parents)
			for (int i = 0; i < c.getParentCount(); i++) {
				outw.print(' ');
				c.getParent(i).getId().copyTo(outbuffer
			}
		outw.println();
	}

	@Override
	protected void show(ObjectWalk ow
			throws Exception {
		if (obj.has(RevFlag.UNINTERESTING))
			outw.print('-');
		obj.getId().copyTo(outbuffer
		final String path = ow.getPathString();
		if (path != null) {
			outw.print(' ');
			outw.print(path);
		} else if (obj instanceof RevTree)
			outw.print(' ');
		outw.println();

	}
}
