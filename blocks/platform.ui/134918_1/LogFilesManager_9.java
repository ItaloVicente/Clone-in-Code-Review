package org.eclipse.ui.internal.views.log;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.*;
import org.eclipse.core.runtime.IStatus;

public class LogEntry extends AbstractEntry {

	public static final String SPACE = " "; //$NON-NLS-1$
	public static final String F_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"; //$NON-NLS-1$
	private static final DateFormat GREGORIAN_SDF = new SimpleDateFormat(F_DATE_FORMAT, Locale.ENGLISH);
	private static final DateFormat LOCAL_SDF = new SimpleDateFormat(F_DATE_FORMAT);

	private String pluginId;
	private int severity;
	private int code;
	private String fDateString;
	private Date fDate;
	private String message;
	private String stack;
	private LogSession session;

	public LogEntry() {
	}

	public LogEntry(IStatus status) {
		this(status, null);
	}

	public LogEntry(IStatus status, LogSession session) {
		processStatus(status, session);
	}

	public LogSession getSession() {
		if ((session == null) && (parent != null) && (parent instanceof LogEntry)) {
			return ((LogEntry) parent).getSession();
		}
		return session;
	}

	void setSession(LogSession session) {
		this.session = session;
	}

	public int getSeverity() {
		return severity;
	}

	public boolean isOK() {
		return severity == IStatus.OK;
	}

	public int getCode() {
		return code;
	}

	public String getPluginId() {
		return pluginId;
	}

	public String getMessage() {
		return message;
	}

	public String getStack() {
		return stack;
	}

	public String getFormattedDate() {
		if (fDateString == null) {
			fDateString = LOCAL_SDF.format(getDate());
		}
		return fDateString;
	}

	public Date getDate() {
		if (fDate == null) {
			fDate = new Date(0); // unknown date - return epoch
		}
		return fDate;
	}

	public String getSeverityText() {
		switch (severity) {
			case IStatus.ERROR : {
				return Messages.LogView_severity_error;
			}
			case IStatus.WARNING : {
				return Messages.LogView_severity_warning;
			}
			case IStatus.INFO : {
				return Messages.LogView_severity_info;
			}
			case IStatus.OK : {
				return Messages.LogView_severity_ok;
			}
		}
		return "?"; //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return getSeverityText();
	}

	@Override
	public String getLabel(Object obj) {
		return getSeverityText();
	}

	public void processEntry(String line) throws ParseException {
		StringTokenizer stok = new StringTokenizer(line, SPACE);
		severity = 0;
		code = 0;
		StringBuilder dateBuffer = new StringBuilder();
		int tokens = stok.countTokens();
		String token = null;
		for (int i = 0; i < tokens; i++) {
			token = stok.nextToken();
			switch (i) {
				case 0 : {
					break;
				}
				case 1 : {
					pluginId = token;
					break;
				}
				case 2 : {
					try {
						severity = Integer.parseInt(token);
					} catch (NumberFormatException nfe) {
						appendToken(dateBuffer, token);
					}
					break;
				}
				case 3 : {
					try {
						code = Integer.parseInt(token);
					} catch (NumberFormatException nfe) {
						appendToken(dateBuffer, token);
					}
					break;
				}
				default : {
					appendToken(dateBuffer, token);
				}
			}
		}
		Date date = GREGORIAN_SDF.parse(dateBuffer.toString());
		if (date != null) {
			fDate = date;
			fDateString = LOCAL_SDF.format(fDate);
		}
	}

	void appendToken(StringBuilder buffer, String token) {
		if (buffer.length() > 0) {
			buffer.append(SPACE);
		}
		buffer.append(token);
	}

	public int processSubEntry(String line) throws ParseException {
		StringTokenizer stok = new StringTokenizer(line, SPACE);
		StringBuilder dateBuffer = new StringBuilder();
		int depth = 0;
		String token = null;
		int tokens = stok.countTokens();
		for (int i = 0; i < tokens; i++) {
			token = stok.nextToken();
			switch (i) {
				case 0 : {
					break;
				}
				case 1 : {
					depth = Integer.parseInt(token);
					break;
				}
				case 2 : {
					pluginId = token;
					break;
				}
				case 3 : {
					try {
						severity = Integer.parseInt(token);
					} catch (NumberFormatException nfe) {
						appendToken(dateBuffer, token);
					}
					break;
				}
				case 4 : {
					try {
						code = Integer.parseInt(token);
					} catch (NumberFormatException nfe) {
						appendToken(dateBuffer, token);
					}
					break;
				}
				default : {
					appendToken(dateBuffer, token);
				}
			}
		}
		Date date = GREGORIAN_SDF.parse(dateBuffer.toString());
		if (date != null) {
			fDate = date;
			fDateString = LOCAL_SDF.format(fDate);
		}
		return depth;
	}

	void setStack(String stack) {
		this.stack = stack;
	}

	void setMessage(String message) {
		this.message = message;
	}

	private void processStatus(IStatus status, LogSession session) {
		pluginId = status.getPlugin();
		severity = status.getSeverity();
		code = status.getCode();
		fDate = new Date();
		fDateString = LOCAL_SDF.format(fDate);
		message = status.getMessage();
		this.session = session;
		Throwable throwable = status.getException();
		if (throwable != null) {
			StringWriter swriter = new StringWriter();
			try (PrintWriter pwriter = new PrintWriter(swriter)) {
				throwable.printStackTrace(pwriter);
				pwriter.flush();
			}
			stack = swriter.toString();
		}
		IStatus[] schildren = status.getChildren();
		if (schildren.length > 0) {
			for (IStatus element : schildren) {
				addChild(new LogEntry(element, session));
			}
		}
	}

	@Override
	public void write(PrintWriter writer) {
		if (session != null) {
			writer.println(session.getSessionData());
		}
		writer.println(pluginId);
		writer.println(getSeverityText());
		if (fDate != null) {
			writer.println(getDate());
		}
		if (message != null) {
			writer.println(getMessage());
		}
		if (stack != null) {
			writer.println();
			writer.println(stack);
		}
	}
}
