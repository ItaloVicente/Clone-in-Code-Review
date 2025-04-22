		case TAG: {
			Ref myRef = (Ref) myObject;
			Ref otherRef = (Ref) otherObject;
			return Objects.equals(myRef.getName(), otherRef.getName())
					&& Objects.equals(myRef.getObjectId(),
							otherRef.getObjectId());
		}
