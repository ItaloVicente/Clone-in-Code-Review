		return new Iterable<PushCertificate>() {
			@Override
			public Iterator<PushCertificate> iterator() {
				return new Iterator<PushCertificate>() {
					private final String path = pathName(refName);
					private PushCertificate next;
