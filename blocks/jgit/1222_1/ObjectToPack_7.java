	boolean isEdge() {
		return (flags & EDGE) != 0;
	}

	void setEdge() {
		flags |= EDGE;
	}

