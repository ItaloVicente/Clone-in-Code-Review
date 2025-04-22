package org.eclipse.ui.internal.monitoring;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.monitoring.StackSample;
import org.eclipse.ui.monitoring.UiFreezeEvent;

public class FilterHandler {
	private static final String DOUBLE_BACKSLASH = "\\\\"; //$NON-NLS-1$

	private final CompoundName compoundName = new CompoundName("", ""); //$NON-NLS-1$//$NON-NLS-2$

	private static class StackFrame implements Comparable<StackFrame> {
		final String className;
		final String methodName;

		public StackFrame(String className, String methodName) {
			this.className = className;
			this.methodName = methodName;
		}

		@Override
		public int compareTo(StackFrame other) {
			int c = methodName.compareTo(other.methodName);
			if (c != 0) {
				return c;
			}
			return className.compareTo(other.className);
		}
	}

	private static class CompoundName implements CharSequence {
		private String first;
		private String last;

		CompoundName(String first, String last) {
			Assert.isNotNull(first);
			Assert.isNotNull(last);
			this.first = first;
			this.last = last;
		}

		void reset(String first, String last) {
			Assert.isNotNull(first);
			Assert.isNotNull(last);
			this.first = first;
			this.last = last;
		}

		@Override
		public int length() {
			return first.length() + 1 + last.length();
		}

		@Override
		public char charAt(int index) {
			int firstLen = first.length();
			if (index < firstLen) {
				return first.charAt(index);
			} else if (index == firstLen) {
				return '.';
			} else {
				return last.charAt(index - firstLen - 1);
			}
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			int lastOffset = first.length() + 1; // Offset of the last name in the sequence.
			if (end < lastOffset) {
				return first.subSequence(start, end);
			} else if (start < lastOffset) {
				return new CompoundName(first.substring(start), last.substring(0, end - lastOffset));
			} else {
				return last.subSequence(start - lastOffset, end - lastOffset);
			}
		}

		@Override
		public String toString() {
			return first + '.' + last;
		}
	}

	private final StackFrame[] filterFrames;
	private final Pattern[] filterPatterns;

	public FilterHandler(String commaSeparatedMethods) {
		String[] filters = commaSeparatedMethods.split(","); //$NON-NLS-1$

		List<StackFrame> stackFrames = new ArrayList<StackFrame>(filters.length);
		List<Pattern> stackPatterns = new ArrayList<Pattern>(filters.length);
		for (String filter : filters) {
			if (containsWildcards(filter)) {
				Pattern pattern = createPattern(filter);
				stackPatterns.add(pattern);
			} else {
				int lastDot = filter.lastIndexOf('.');
				stackFrames.add(lastDot >= 0 ?
						new StackFrame(filter.substring(0, lastDot), filter.substring(lastDot + 1)) :
						new StackFrame("", filter)); //$NON-NLS-1$
			}
		}

		Collections.sort(stackFrames);
		filterFrames = stackFrames.toArray(new StackFrame[stackFrames.size()]);
		filterPatterns = stackPatterns.toArray(new Pattern[stackPatterns.size()]);
	}

	public boolean shouldLogEvent(StackSample[] stackSamples, int numSamples,
			long displayThreadId) {
		if (filterFrames.length != 0 || filterPatterns.length != 0) {
			for (int i = 0; i < numSamples; i++) {
				if (hasFilteredTraces(stackSamples[i].getStackTraces(), displayThreadId)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasFilteredTraces(ThreadInfo[] stackTraces, long displayThreadId) {
		for (ThreadInfo threadInfo : stackTraces) {
			if (threadInfo.getThreadId() == displayThreadId) {
				for (StackTraceElement element : threadInfo.getStackTrace()) {
					if (matchesFilter(element)) {
						return true;
					}
				}
				return false;
			}
		}

		MonitoringPlugin.logError(Messages.FilterHandler_missing_thread_error, null);
		return false;
	}

	boolean matchesFilter(StackTraceElement stackFrame) {
		String className = stackFrame.getClassName();
		String methodName = stackFrame.getMethodName();
		if (filterPatterns.length != 0) {
			compoundName.reset(className, methodName);
			for (Pattern pattern : filterPatterns) {
				if (pattern.matcher(compoundName).matches()) {
					return true;
				}
			}
		}
		int low = 0;
		int high = filterFrames.length;
		while (low < high) {
			int mid = (low + high) >>> 1;
			StackFrame filter = filterFrames[mid];
			int c = methodName.compareTo(filter.methodName);
			if (c == 0) {
				c = className.compareTo(filter.className);
			}
			if (c == 0) {
				return true;
			} else if (c < 0) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return false;
	}

	private boolean containsWildcards(String pattern) {
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c != '.' && !Character.isJavaIdentifierPart(c)) {
				return true;
			}
		}
		return false;
	}

	private static Pattern createPattern(String pattern) throws PatternSyntaxException {
		int len = pattern.length();
		StringBuilder buf = new StringBuilder(len * 2);
		boolean isEscaped = false;
		for (int i = 0; i < len; i++) {
		    char c = pattern.charAt(i);
		    switch (c) {
		    case '\\':
		        if (!isEscaped) {
		            isEscaped = true;
		        } else {
		            buf.append(DOUBLE_BACKSLASH);
		            isEscaped = false;
		        }
		        break;
		    case '(':
		    case ')':
		    case '{':
		    case '}':
		    case '.':
		    case '[':
		    case ']':
		    case '$':
		    case '^':
		    case '+':
		    case '|':
		        if (isEscaped) {
		            buf.append(DOUBLE_BACKSLASH);
		            isEscaped = false;
		        }
		        buf.append('\\');
		        buf.append(c);
		        break;
		    case '?':
		        if (!isEscaped) {
		            buf.append('.');
		        } else {
		            buf.append('\\');
		            buf.append(c);
		            isEscaped = false;
		        }
		        break;
		    case '*':
		        if (!isEscaped) {
		            buf.append(".*"); //$NON-NLS-1$
		        } else {
		            buf.append('\\');
		            buf.append(c);
		            isEscaped = false;
		        }
		        break;
		    default:
		        if (isEscaped) {
		            buf.append(DOUBLE_BACKSLASH);
		            isEscaped = false;
		        }
		        buf.append(c);
		        break;
		    }
		}
		if (isEscaped) {
		    buf.append(DOUBLE_BACKSLASH);
		    isEscaped= false;
		}
		return Pattern.compile(buf.toString());
	}
}
