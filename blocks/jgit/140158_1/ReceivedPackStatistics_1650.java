
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_ATOMIC;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_PUSH_OPTIONS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_REPORT_STATUS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;

public class ReceivePack extends BaseReceivePack {
	private PreReceiveHook preReceive;

	private PostReceiveHook postReceive;

	private boolean reportStatus;

	private boolean usePushOptions;
	private List<String> pushOptions;

	public ReceivePack(Repository into) {
		super(into);
		preReceive = PreReceiveHook.NULL;
		postReceive = PostReceiveHook.NULL;
	}

	@Override
	public final Repository getRepository() {
		return db;
	}

	@Override
	public final RevWalk getRevWalk() {
		return walk;
	}

	@Override
	public final Map<String
		return refs;
	}

	@Override
	public void setAdvertisedRefs(Map<String
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);
		advertisedHaves.clear();

		Ref head = refs.get(HEAD);
		if (head != null && head.isSymbolic()) {
			refs.remove(HEAD);
		}

		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null) {
				advertisedHaves.add(ref.getObjectId());
			}
		}
		if (additionalHaves != null) {
			advertisedHaves.addAll(additionalHaves);
		} else {
			advertisedHaves.addAll(db.getAdditionalHaves());
		}
	}

	@Override
	public PushCertificate getPushCertificate() {
		return pushCert;
	}

	@Override
	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

	@Nullable
	public List<String> getPushOptions() {
		if (isAllowPushOptions() && usePushOptions) {
			return Collections.unmodifiableList(pushOptions);
		}

		return null;
	}

	public void setPushOptions(@Nullable List<String> options) {
		usePushOptions = options != null;
		pushOptions = options;
	}

	public PreReceiveHook getPreReceiveHook() {
		return preReceive;
	}

	public void setPreReceiveHook(PreReceiveHook h) {
		preReceive = h != null ? h : PreReceiveHook.NULL;
	}

	public PostReceiveHook getPostReceiveHook() {
		return postReceive;
	}

	public void setPostReceiveHook(PostReceiveHook h) {
		postReceive = h != null ? h : PostReceiveHook.NULL;
	}

	@Deprecated
	public void setEchoCommandFailures(boolean echo) {
	}

	public void receive(final InputStream input
			final OutputStream messages) throws IOException {
		init(input
		try {
			service();
		} finally {
			try {
				close();
			} finally {
				release();
			}
		}
	}

	@Override
	protected void enableCapabilities() {
		reportStatus = isCapabilityEnabled(CAPABILITY_REPORT_STATUS);
		usePushOptions = isCapabilityEnabled(CAPABILITY_PUSH_OPTIONS);
		super.enableCapabilities();
	}

	@Override
	void readPostCommands(PacketLineIn in) throws IOException {
		if (usePushOptions) {
			pushOptions = new ArrayList<>(4);
			for (;;) {
				String option = in.readString();
				if (option == PacketLineIn.END) {
					break;
				}
				pushOptions.add(option);
			}
		}
	}

	private void service() throws IOException {
		if (isBiDirectionalPipe()) {
			sendAdvertisedRefs(new PacketLineOutRefAdvertiser(pckOut));
			pckOut.flush();
		} else
			getAdvertisedOrDefaultRefs();
		if (hasError())
			return;
		recvCommands();
		if (hasCommands()) {
			Throwable unpackError = null;
			if (needPack()) {
				try {
					receivePackAndCheckConnectivity();
				} catch (IOException | RuntimeException | Error err) {
					unpackError = err;
				}
			}

			try {
				if (unpackError == null) {
					boolean atomic = isCapabilityEnabled(CAPABILITY_ATOMIC);
					setAtomic(atomic);

					validateCommands();
					if (atomic && anyRejects()) {
						failPendingCommands();
					}

					preReceive.onPreReceive(
							this
					if (atomic && anyRejects()) {
						failPendingCommands();
					}
					executeCommands();
				}
			} finally {
				unlockPack();
			}

			if (reportStatus) {
				sendStatusReport(true
					@Override
					void sendString(String s) throws IOException {
					}
				});
				pckOut.end();
			} else if (msgOut != null) {
				sendStatusReport(false
					@Override
					void sendString(String s) throws IOException {
					}
				});
			}

			if (unpackError != null) {
				try {
					postReceive.onPostReceive(this
				} catch (Throwable e) {
				}
				throw new UnpackException(unpackError);
			}
			postReceive.onPostReceive(this
			autoGc();
		}
	}

	private void autoGc() {
		Repository repo = getRepository();
		if (!repo.getConfig().getBoolean(ConfigConstants.CONFIG_RECEIVE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOGC
			return;
		}
		repo.autoGC(NullProgressMonitor.INSTANCE);
	}

	@Override
	protected String getLockMessageProcessName() {
	}
}
