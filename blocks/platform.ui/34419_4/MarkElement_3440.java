package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.resources.IFile;

public interface IReadmeFileParser {
    public MarkElement[] parse(IFile readmeFile);
}
