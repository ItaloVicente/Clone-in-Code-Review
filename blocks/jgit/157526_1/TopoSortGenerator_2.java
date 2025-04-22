
			if ((c.flags & TOPO_QUEUED) == 0) {
				continue;
			}

                        c.flags &= ~TOPO_QUEUED;
