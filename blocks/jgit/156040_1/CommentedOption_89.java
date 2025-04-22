public class CommentedOption implements OpenOption

	private final String sessionId;
	private final String name;
	private final String email;
	private final String message;
	private final Date when;
	private final TimeZone timeZone;

	public CommentedOption(final String name) {
		this(null
	}

	public CommentedOption(final String name
		this(null
	}

	public CommentedOption(final String name
		this(null
	}

	public CommentedOption(final String sessionId
		this(sessionId
	}

	public CommentedOption(final String name
		this(null
	}

	public CommentedOption(final String sessionId
			final Date when) {
		this(sessionId
	}

	public CommentedOption(final String name
			final TimeZone timeZone) {
		this(null
	}

	public CommentedOption(final String sessionId
			final Date when
		this.sessionId = sessionId;
		this.name = name;
		this.email = email;
		this.message = message;
		this.when = when;
		this.timeZone = timeZone;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getMessage() {
		return message;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getWhen() {
		return when;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}
