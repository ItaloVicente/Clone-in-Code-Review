	final boolean visited() {
		return (flags & VISITED) != 0;
	}

	final void setVisited() {
		flags |= VISITED;
	}

	final void clearVisited() {
		flags &= ~VISITED;
	}

