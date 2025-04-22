import static org.eclipse.jgit.http.server.ServletUtils.ATTRIBUTE_HANDLER;
import static org.eclipse.jgit.transport.BasePackFetchConnection.OPTION_SIDE_BAND;
import static org.eclipse.jgit.transport.BasePackFetchConnection.OPTION_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_ERROR;
import static org.eclipse.jgit.transport.SideBandOutputStream.SMALL_BUF;
