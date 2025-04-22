			}
		} else {
			if (other.myRepository == null) {
				return false;
			}
			if (!myRepository.getDirectory()
					.equals(other.myRepository.getDirectory())) {
				return false;
			}
		}
