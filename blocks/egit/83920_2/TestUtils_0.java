	private static String getJobNames(Job[] jobs) {
		StringBuilder sb = new StringBuilder();
		for (Job job : jobs) {
			sb.append(job.getName()).append(" / ").append(job.toString())
					.append(", ");
		}
		return sb.toString();
	}

