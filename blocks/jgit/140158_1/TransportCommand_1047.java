package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidTagNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

public class TagCommand extends GitCommand<Ref> {
	private RevObject id;

	private String name;

	private String message;

	private PersonIdent tagger;

	private boolean signed;

	private boolean forceUpdate;

	private boolean annotated = true;

	protected TagCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Ref call() throws GitAPIException
			InvalidTagNameException
		checkCallable();

		RepositoryState state = repo.getRepositoryState();
		processOptions(state);

		try (RevWalk revWalk = new RevWalk(repo)) {
			if (id == null) {
				if (objectId == null)
					throw new NoHeadException(
							JGitText.get().tagOnRepoWithoutHEADCurrentlyNotSupported);

				id = revWalk.parseCommit(objectId);
			}

			if (!annotated) {
				if (message != null || tagger != null)
					throw new JGitInternalException(
							JGitText.get().messageAndTaggerNotAllowedInUnannotatedTags);
				return updateTagRef(id
			}

			TagBuilder newTag = new TagBuilder();
			newTag.setTag(name);
			newTag.setMessage(message);
			newTag.setTagger(tagger);
			newTag.setObjectId(id);

			try (ObjectInserter inserter = repo.newObjectInserter()) {
				ObjectId tagId = inserter.insert(newTag);
				inserter.flush();

				String tag = newTag.getTag();
				return updateTagRef(tagId

			}

		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfTagCommand
					e);
		}
	}

	private Ref updateTagRef(ObjectId tagId
			String tagName
			ConcurrentRefUpdateException
		String refName = Constants.R_TAGS + tagName;
		RefUpdate tagRef = repo.updateRef(refName);
		tagRef.setNewObjectId(tagId);
		tagRef.setForceUpdate(forceUpdate);
		tagRef.setRefLogMessage("tagged " + name
		Result updateResult = tagRef.update(revWalk);
		switch (updateResult) {
		case NEW:
		case FORCED:
			return repo.exactRef(refName);
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					JGitText.get().couldNotLockHEAD
					updateResult);
		case REJECTED:
			throw new RefAlreadyExistsException(MessageFormat.format(
					JGitText.get().tagAlreadyExists
		default:
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().updatingRefFailed
					updateResult));
		}
	}

	private void processOptions(RepositoryState state)
			throws InvalidTagNameException {
		if (tagger == null && annotated)
			tagger = new PersonIdent(repo);
		if (name == null || !Repository.isValidRefName(Constants.R_TAGS + name))
			throw new InvalidTagNameException(
					MessageFormat.format(JGitText.get().tagNameInvalid
		if (signed)
			throw new UnsupportedOperationException(
					JGitText.get().signingNotSupportedOnTag);
	}

	public TagCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public TagCommand setMessage(String message) {
		checkCallable();
		this.message = message;
		return this;
	}

	public boolean isSigned() {
		return signed;
	}

	public TagCommand setSigned(boolean signed) {
		this.signed = signed;
		return this;
	}

	public TagCommand setTagger(PersonIdent tagger) {
		this.tagger = tagger;
		return this;
	}

	public PersonIdent getTagger() {
		return tagger;
	}

	public RevObject getObjectId() {
		return id;
	}

	public TagCommand setObjectId(RevObject id) {
		this.id = id;
		return this;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public TagCommand setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
		return this;
	}

	public TagCommand setAnnotated(boolean annotated) {
		this.annotated = annotated;
		return this;
	}

	public boolean isAnnotated() {
		return annotated;
	}
}
