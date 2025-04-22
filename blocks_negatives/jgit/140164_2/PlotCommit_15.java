		if (cnt == 0)
			children = new PlotCommit[] { c };
		else if (cnt == 1) {
			if (!c.getId().equals(children[0].getId()))
				children = new PlotCommit[] { children[0], c };
		} else {
			for (PlotCommit pc : children)
				if (c.getId().equals(pc.getId()))
					return;
			final PlotCommit[] n = new PlotCommit[cnt + 1];
			System.arraycopy(children, 0, n, 0, cnt);
			n[cnt] = c;
			children = n;
		}
