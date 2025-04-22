
package org.eclipse.jgit.niofs.internal.daemon.filter;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RefFilter;

public class HiddenBranchRefFilter implements RefFilter {

    private static final String HIDDEN_BRANCH_REGEXP = "PR-\\d+-\\S+-\\S+";
    private static Pattern pattern = Pattern.compile(HIDDEN_BRANCH_REGEXP);

    @Override
    public Map<String
        return refs.entrySet()
                .stream()
                .filter(ref -> !HiddenBranchRefFilter.isHidden(ref.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey
                                          Map.Entry::getValue));
    }

    public static boolean isHidden(String branch) {
        return pattern.matcher(branch).matches();
    }
}
