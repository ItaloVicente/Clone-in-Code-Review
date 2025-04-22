			sb.append(" (forced)");
		sb.append(" ");
		sb.append(oldObjectId == null ? "" : oldObjectId.abbreviate(7).name());
		sb.append("..");
		sb.append(newObjectId == null ? "" : newObjectId.abbreviate(7).name());
		sb.append("]");
