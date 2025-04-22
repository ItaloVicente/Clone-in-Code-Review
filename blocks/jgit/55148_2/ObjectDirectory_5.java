		static class Id {
			String alternateId;

			public Id(File object) {
				try {
					this.alternateId = object.getCanonicalPath();
				} catch (Exception e) {
					alternateId = null;
				}
			}

			@Override
			public boolean equals(Object o) {
				if (o == this)
					return true;
				if (o == null || ! (o instanceof Id))
					return false;
				Id aId = (Id) o;
				return Objects.equals(alternateId
			}

			@Override
			public int hashCode() {
				if (alternateId == null)
					return 1;
				return alternateId.hashCode();
			}
		}

