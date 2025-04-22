			if (a.isSymbolic() != b.isSymbolic())
				return false;
			if (a.isSymbolic())
				return Objects.equals(a.getTarget().getName()
			else
				return Objects.equals(a.getObjectId()
