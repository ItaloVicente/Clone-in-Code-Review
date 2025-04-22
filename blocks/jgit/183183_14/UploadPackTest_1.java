		assertThat(pckIn.readString()
				is(tip.toObjectId().getName() + " HEAD"));
		assertThat(pckIn.readString()
				is(tip.toObjectId().getName() + " refs/heads/master"));
		assertThat(pckIn.readString()
				is(tag.toObjectId().getName() + " refs/tags/tag"));
