		else if (cnt == 1) {
			if (!c.getId().equals(children[0].getId()))
				children = new PlotCommit[] { children[0]
		} else {
			for (PlotCommit pc : children)
				if (c.getId().equals(pc.getId()))
					return;
