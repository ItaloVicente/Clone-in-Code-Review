		else if (cnt == 1) {
			if (!c.getId().equals(children[0].getId()))
				children = new PlotCommit[] { children[0]
		} else {
			for (int i = 1; i < children.length; i++)
				if (c.getId().equals(children[i].getId()))
					return;
