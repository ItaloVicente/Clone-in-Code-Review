		if (!(o instanceof ProgramImageDescriptor)) {
			return false;
		}
		ProgramImageDescriptor other = (ProgramImageDescriptor) o;
		return filename.equals(other.filename) && offset == other.offset;
	}
