		assertThat(pckIn.readString()
				+ " HEAD symref-target:refs/heads/master"));
		assertThat(pckIn.readString()
				is(tip.toObjectId().getName() + " refs/heads/master"));
		assertThat(pckIn.readString()
				is(tag.toObjectId().getName() + " refs/tags/tag"));
