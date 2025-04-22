				case NEW:
				case FORCED:
				case FAST_FORWARD:
					break;
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(MessageFormat
							.format(JGitText.get().cannotLock
							ru.getRef()
				default:
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().updatingRefFailed
							commitId.name()
