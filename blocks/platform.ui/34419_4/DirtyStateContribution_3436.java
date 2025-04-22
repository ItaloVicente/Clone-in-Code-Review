package org.eclipse.ui.examples.readmetool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;

public class DefaultSectionsParser implements IReadmeFileParser {
    protected IAdaptable getParent(Hashtable<String, MarkElement> toc, String number) {
        int lastDot = number.lastIndexOf('.');
        if (lastDot < 0)
            return null;
        String parentNumber = number.substring(0, lastDot);
        return toc.get(parentNumber);
    }

    protected String getText(IFile file) {
        try {
            InputStream in = file.getContents();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int read = in.read(buf);
            while (read > 0) {
                out.write(buf, 0, read);
                read = in.read(buf);
            }
            return out.toString();
        } catch (CoreException e) {
        } catch (IOException e) {
        }
        return ""; //$NON-NLS-1$
    }

    @Override
	public MarkElement[] parse(IFile file) {
        Hashtable<String, MarkElement> markTable = new Hashtable<>(40);
        Vector<MarkElement> topLevel = new Vector<>();
        String s = getText(file);
        int start = 0;
        int end = -1;
        int lineno = 0;
        int lastlineno = 0;
        MarkElement lastme = null;
        int ix;

        ix = s.indexOf('\n', start);
        while (ix != -1) {
            start = end + 1;
            end = ix = s.indexOf('\n', start);
            lineno++;
            if (ix != -1) {
                while (s.charAt(start) == ' ' || s.charAt(start) == '\t') {
                    start++;
                }
                if (Character.isDigit(s.charAt(start))) {
                    if (lastme != null) {
                        lastme.setNumberOfLines(lineno - lastlineno - 1);
                    }
                    lastlineno = lineno;
                    String markName = parseHeading(s, start, end);

                    String markNumber = parseNumber(markName);
                    IAdaptable parent = getParent(markTable, markNumber);
                    if (parent == null)
                        parent = file;

                    MarkElement me = new MarkElement(parent, markName, start,
                            end - start);
                    lastme = me;

                    markTable.put(markNumber, me);
                    if (parent == file) {
                        topLevel.add(me);
                    }
                }
            }
        }
        if (lastme != null) {
            lastme.setNumberOfLines(lineno - lastlineno - 1);
        }
        MarkElement[] results = new MarkElement[topLevel.size()];
        topLevel.copyInto(results);
        return results;
    }

    private String parseHeading(String buffer, int start, int end) {
        while (Character.isWhitespace(buffer.charAt(end - 1)) && end > start) {
            end--;
        }
        return buffer.substring(start, end);
    }

    protected String parseNumber(String heading) {
        int start = 0;
        int end = heading.length();
        char c;
        do {
            c = heading.charAt(start++);
        } while ((c == '.' || Character.isDigit(c)) && start < end);

        while (heading.charAt(start - 1) == '.' && start > 0) {
            start--;
        }
        return heading.substring(0, start);
    }
}
