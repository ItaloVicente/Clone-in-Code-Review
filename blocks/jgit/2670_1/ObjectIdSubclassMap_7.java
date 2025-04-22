		V addIfAbsent(V newValue) {
			int h = hash(newValue);
			int n = CHAIN_LENGTH;
			V obj;
			while ((obj = members[h]) != null) {
				if (AnyObjectId.equals(obj
					return obj;
				if (++h == SIZE)
					h = 0;
				if (--n == 0)
					return null;
			}
			members[h] = newValue;
			return newValue;
		}

		void appendExisting(V newValue) {
			int h = hash(newValue);
			int n = CHAIN_LENGTH;
			while (members[h] != null) {
				if (++h == SIZE)
					h = 0;
				if (--n == 0)
					throw new IllegalStateException("chain length exceeded");
			}
			members[h] = newValue;
