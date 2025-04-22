
		if (tips.size() == 1)
			this.tips = Collections.singleton(tips.iterator().next());
		else
			this.tips = Collections.unmodifiableSet(tips);

