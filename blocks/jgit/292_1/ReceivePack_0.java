import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_DELETE_REFS;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_OFS_DELTA;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_REPORT_STATUS;
import static org.eclipse.jgit.transport.BasePackPushConnection.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_DATA;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_PROGRESS;
import static org.eclipse.jgit.transport.SideBandOutputStream.MAX_BUF;

