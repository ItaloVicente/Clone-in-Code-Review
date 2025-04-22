package org.eclipse.egit.ui.internal;

import java.io.IOException;

import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.clone.ProjectRecord;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelObject;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelRepository;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jgit.lib.BranchTrackingStatus;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.osgi.util.NLS;

public class GitLabels {
	private GitLabels() {
	}

	public static String getRefDescription(Ref ref) {
		String name = ref.getName();
		if (name.equals(Constants.HEAD)) {
			if (ref.isSymbolic())
				return UIText.GitLabelProvider_RefDescriptionHeadSymbolic;
			else
				return UIText.GitLabelProvider_RefDescriptionHead;
		} else if (name.equals(Constants.ORIG_HEAD))
			return UIText.GitLabelProvider_RefDescriptionOrigHead;
		else if (name.equals(Constants.FETCH_HEAD))
			return UIText.GitLabelProvider_RefDescriptionFetchHead;
		else if (name.equals(Constants.R_STASH))
			return UIText.GitLabelProvider_RefDescriptionStash;
		else
			return null;
	}

	public static String formatBranchTrackingStatus(BranchTrackingStatus status) {
		StringBuilder sb = new StringBuilder();
		int ahead = status.getAheadCount();
		int behind = status.getBehindCount();
		if (ahead != 0) {
			sb.append('\u2191');
			sb.append(ahead);
		}
		if (behind != 0) {
			if (sb.length() != 0)
				sb.append(' ');
			sb.append('\u2193');
			sb.append(status.getBehindCount());
		}
		return sb.toString();
	}

	public static StyledString getStyledLabel(Repository repository)
			throws IOException {
		RepositoryUtil repositoryUtil = Activator.getDefault()
				.getRepositoryUtil();

		StyledString string = new StyledString();
		string.append(repositoryUtil.getRepositoryName(repository));

		String branch = repositoryUtil.getShortBranch(repository);
		if (branch != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			string.append(branch, StyledString.DECORATIONS_STYLER);

			BranchTrackingStatus trackingStatus = BranchTrackingStatus.of(
					repository, branch);
			if (trackingStatus != null
					&& (trackingStatus.getAheadCount() != 0 || trackingStatus
							.getBehindCount() != 0)) {
				String formattedTrackingStatus = GitLabels
						.formatBranchTrackingStatus(trackingStatus);
				string.append(' ');
				string.append(formattedTrackingStatus,
						StyledString.DECORATIONS_STYLER);
			}

			RepositoryState repositoryState = repository.getRepositoryState();
			if (repositoryState != RepositoryState.SAFE) {
				string.append(" - ", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
				string.append(repositoryState.getDescription(),
						StyledString.DECORATIONS_STYLER);
			}
			string.append(']', StyledString.DECORATIONS_STYLER);
		}

		return string;
	}

	public static StyledString getStyledLabelSafe(Repository repository) {
		try {
			return getStyledLabel(repository);
		} catch (IOException e) {
			logLabelRetrievalFailure(repository, e);
		}
		return new StyledString(getPlainShortLabel(repository));
	}

	public static StyledString getStyledLabelExtendedSafe(Object element) {
		Repository repo = asRepository(element);

		if (repo != null) {
			try {
				StyledString text = getStyledLabel(repo);
				text.append(getLabelExtension(repo),
						StyledString.QUALIFIER_STYLER);
				return text;
			} catch (IOException e) {
				logLabelRetrievalFailure(element, e);
			}
		}
		return new StyledString(getPlainShortLabelExtended(element));
	}

	public static String getPlainShortLabelExtended(Object element) {
		return getPlainShortLabel(element) + getLabelExtension(element);
	}

	private static void logLabelRetrievalFailure(Object element, IOException e) {
		Activator.logError(
				NLS.bind(UIText.GitLabelProvider_UnableToRetrieveLabel,
						element.toString()), e);
	}

	private static String getLabelExtension(Object element) {
		Repository repo = asRepository(element);

		if (repo != null)
			return " - " + getRepositoryAbsolutePath(repo); //$NON-NLS-1$
		else
			return ""; //$NON-NLS-1$
	}

	public static String getPlainShortLabel(Object element) {
		if (element instanceof Repository)
			return getRepositorySimpleLabel((Repository) element);

		if (element instanceof RefNode)
			return getRefNodeSimpleLabel((RefNode) element);

		if (element instanceof Ref)
			return ((Ref) element).getName();

		if (element instanceof ProjectRecord)
			return ((ProjectRecord) element).getProjectLabel();

		if (element instanceof GitModelObject)
			return ((GitModelObject) element).getName();

		return (element != null ? element.toString() : ""); //$NON-NLS-1$
	}

	private static String getRefNodeSimpleLabel(RefNode refNode) {
		return refNode.getObject().getName();
	}

	private static String getRepositorySimpleLabel(Repository repository) {
		return Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
	}

	private static String getRepositoryAbsolutePath(Repository repository) {
		return repository.getDirectory().getAbsolutePath();
	}

	private static Repository asRepository(Object element) {
		if (element instanceof Repository)
			return (Repository) element;
		else if (element instanceof GitModelRepository)
			return ((GitModelRepository) element).getRepository();
		else
			return null;
	}
}
