			hashCode = HASH_INITIAL * HASH_FACTOR + Util.hashCode(command);
			hashCode = hashCode * HASH_FACTOR;
			if (parameterizations != null) {
				for (Parameterization parameterization : parameterizations) {
					hashCode += Util.hashCode(parameterization);
				}
			}
