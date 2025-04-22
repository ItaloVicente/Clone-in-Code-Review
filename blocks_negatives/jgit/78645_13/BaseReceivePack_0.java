import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_ATOMIC;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_DELETE_REFS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_OFS_DELTA;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_QUIET;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_REPORT_STATUS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_PUSH_OPTIONS;
import static org.eclipse.jgit.transport.GitProtocolConstants.CAPABILITY_SIDE_BAND_64K;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_DATA;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_ERROR;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_PROGRESS;
import static org.eclipse.jgit.transport.SideBandOutputStream.MAX_BUF;

import java.io.EOFException;
