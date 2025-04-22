		assertThat(pckIn.readString()
				+ " HEAD symref-target:refs/heads/master"));
		assertThat(pckIn.readString()
				is(tip.toObjectId().getName() + " refs/heads/master"));
		assertThat(pckIn.readString()
				+ " refs/tags/tag peeled:" + tip.toObjectId().getName()));
