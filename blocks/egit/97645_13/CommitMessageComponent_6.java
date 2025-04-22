package org.eclipse.egit.ui.internal.dialogs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.core.internal.util.ProjectUtil;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.ICommitMessageProvider;
import org.eclipse.egit.ui.ICommitMessageProvider2;
import org.eclipse.egit.ui.ICommitMessageProvider2.CommitMessageWithCaretPosition;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.lib.Repository;

class CommitMessageCalculator {

	private static final String COMMIT_MESSAGE_PROVIDER_ID = "org.eclipse.egit.ui.commitMessageProvider"; //$NON-NLS-1$

	private static final String MESSAGE_SEPARATOR = "\n\n"; //$NON-NLS-1$

	private final IResource[] resourcesArray;

	private Repository repository;

	private List<ICommitMessageProvider> providers;

	CommitMessageCalculator(Repository repository,
			Collection<String> filesToCommit) {
		this.repository = repository;
		this.resourcesArray = convertToRessourceArray(filesToCommit);
		this.providers = getCommitMessageProviders();
	}

	CommitMessageWithCaretPosition calculateCommitMessageAndCaretPosition() {
		StringBuilder finalMessage = new StringBuilder();
		int caretPosition = CommitMessageWithCaretPosition.NO_POSITION;

		for (ICommitMessageProvider provider : providers) {
			String messagePart = ""; //$NON-NLS-1$

			try {
				if (provider instanceof ICommitMessageProvider2) {
					CommitMessageWithCaretPosition commitMessageWithPosition = ((ICommitMessageProvider2) provider)
							.getCommitMessageWithPosition(resourcesArray);
					messagePart = getCommitMessage(
							commitMessageWithPosition,
							finalMessage.length() == 0);

					if (commitMessageWithPosition != null) {
						caretPosition = getUpdatedCaretPosition(finalMessage,
								caretPosition, commitMessageWithPosition,
								(ICommitMessageProvider2) provider);
					}

				} else {
					messagePart = getCommitMessage(provider,
							finalMessage.length() == 0);
				}
			} catch (RuntimeException e) {
				Activator.logError(e.getMessage(), e);
			}

			finalMessage.append(messagePart);
		}

		return new CommitMessageWithCaretPosition(finalMessage.toString(),
				Math.max(0, caretPosition));
	}

	private String getCommitMessage(
			CommitMessageWithCaretPosition messageWithPosition,
			boolean isFirstMessage) {
		if (messageWithPosition == null) {
			return ""; //$NON-NLS-1$
		} else {
			return buildCommitMessage(messageWithPosition.getMessage(),
					isFirstMessage);
		}
	}

	private String getCommitMessage(ICommitMessageProvider provider,
			boolean isFirstMessage) {
		return buildCommitMessage(provider.getMessage(resourcesArray),
				isFirstMessage);
	}

	private String buildCommitMessage(String msg, boolean isFirstMessage) {
		StringBuilder returnMsg = new StringBuilder();

		if (msg != null && !msg.trim().isEmpty()) {
			if (!isFirstMessage) {
				returnMsg.append(MESSAGE_SEPARATOR);
			}
			returnMsg.append(msg);
		}

		return returnMsg.toString();
	}

	@SuppressWarnings("boxing")
	private int getUpdatedCaretPosition(StringBuilder currentCalculatedMessage,
			int currentCaretPosition,
			CommitMessageWithCaretPosition messageWithPosition,
			ICommitMessageProvider2 provider) {
		int pos = currentCaretPosition;

		if (currentCaretPosition == CommitMessageWithCaretPosition.NO_POSITION) {

			String providedMessage = messageWithPosition.getMessage();
			if (providedMessage == null || providedMessage.trim().isEmpty()) {
				return pos;
			}

			int providedCaretPosition = messageWithPosition
					.getDesiredCaretPosition();
			if (providedCaretPosition == CommitMessageWithCaretPosition.NO_POSITION) {
				return pos;
			}

			if (providedCaretPosition > providedMessage.length()
					|| providedCaretPosition < 0) {
				Activator.logWarning(
						MessageFormat.format(
								UIText.CommitDialog_CaretPositionOutOfBounds,
								provider.getClass().getName(),
								providedCaretPosition),
						null);
				return CommitMessageWithCaretPosition.NO_POSITION;

			} else {
				pos = currentCalculatedMessage.length();
				if (currentCalculatedMessage.length() > 0) {
					pos += MESSAGE_SEPARATOR.length();
				}
				pos += providedCaretPosition;
			}
		} else {
			Activator
					.logWarning(
							MessageFormat.format(
									UIText.CommitDialog_MultipleCommitMessageProvider2Implementations,
									provider.getClass().getName()),
							null);
		}

		return pos;
	}

	List<ICommitMessageProvider> getCommitMessageProviders() {
		List<ICommitMessageProvider> foundProviders = new ArrayList<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] configs = registry
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		for (IConfigurationElement config : configs) {
			Object provider;
			String contributorName = "<unknown>"; //$NON-NLS-1$
			String extensionId = "<unknown>"; //$NON-NLS-1$
			try {
				extensionId = config.getDeclaringExtension()
						.getUniqueIdentifier();
				contributorName = config.getContributor().getName();
				provider = config.createExecutableExtension("class");//$NON-NLS-1$
				if (provider instanceof ICommitMessageProvider) {
					foundProviders.add((ICommitMessageProvider) provider);
				} else {
					Activator.logError(
							MessageFormat.format(
									UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
									extensionId, contributorName),
							null);
				}
			} catch (CoreException | RuntimeException e) {
				Activator
						.logError(
								MessageFormat.format(
										UIText.CommitDialog_ErrorCreatingCommitMessageProvider,
										extensionId, contributorName),
								e);
			}
		}
		return foundProviders;
	}

	private IResource[] convertToRessourceArray(
			Collection<String> filesToCommit) {

		Set<IResource> resources = new HashSet<>();
		for (String path : filesToCommit) {
			IFile file = ResourceUtil.getFileForLocation(repository, path,
					false);
			if (file != null)
				resources.add(file.getProject());
		}
		if (resources.size() == 0 && repository != null) {
			resources
					.addAll(Arrays.asList(ProjectUtil.getProjects(repository)));
		}

		return resources.toArray(new IResource[0]);
	}

}
