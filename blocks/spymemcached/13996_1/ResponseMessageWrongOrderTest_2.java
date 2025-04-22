
package net.spy.memcached.tapmessage;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import org.junit.Before;

public class ResponseMessageFixedOrderTest extends ResponseMessageBaseCase {

  @Before
  @Override
  public void setUp() {


    expectedFlags = new LinkedList<TapResponseFlag>();
    expectedFlags.add(TapResponseFlag.TAP_FLAG_NETWORK_BYTE_ORDER);

    ByteBuffer binReqTapMutation = ByteBuffer.allocate(24+16+1+8);

    binReqTapMutation.put(0, (byte)0x80).put(1, (byte)0x41);
    binReqTapMutation.put(3, (byte)0x01); // key length
    binReqTapMutation.put(5, (byte)0x06); // datatype
    binReqTapMutation.put(11, (byte)0x09); // body length 1 key 8 value
    binReqTapMutation.put(27, (byte)0x04); // TAP_FLAG_NETWORK_BYTE_ORDER; fixed
    binReqTapMutation.put(34, (byte)0x02);

    binReqTapMutation.put(40, (byte)'a');
    binReqTapMutation.put(48, (byte)42);


    responsebytes = binReqTapMutation.array();

    instance = new ResponseMessage(responsebytes);
  }




}
