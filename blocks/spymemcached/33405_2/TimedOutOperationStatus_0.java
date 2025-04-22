
package net.spy.memcached.ops;

public enum StatusCode {

  SUCCESS,
  ERR_NOT_FOUND,
  ERR_EXISTS,
  ERR_2BIG,
  ERR_INVAL,
  ERR_NOT_STORED,
  ERR_DELTA_BADVAL,
  ERR_NOT_MY_VBUCKET,
  ERR_UNKNOWN_COMMAND,
  ERR_NO_MEM,
  ERR_NOT_SUPPORTED,
  ERR_INTERNAL,
  ERR_BUSY,
  ERR_TEMP_FAIL,
  CANCELLED,
  INTERRUPTED,
  TIMEDOUT;

  public static StatusCode fromBinaryCode(int code) {
    switch(code) {
      case 0x00:
        return SUCCESS;
      case 0x01:
        return ERR_NOT_FOUND;
      case 0x02:
        return ERR_EXISTS;
      case 0x03:
        return ERR_2BIG;
      case 0x04:
        return ERR_INVAL;
      case 0x05:
        return ERR_NOT_STORED;
      case 0x06:
        return ERR_DELTA_BADVAL;
      case 0x07:
        return ERR_NOT_MY_VBUCKET;
      case 0x81:
        return ERR_UNKNOWN_COMMAND;
      case 0x82:
        return ERR_NO_MEM;
      case 0x83:
        return ERR_NOT_SUPPORTED;
      case 0x84:
        return ERR_INTERNAL;
      case 0x85:
        return ERR_BUSY;
      case 0x86:
        return ERR_TEMP_FAIL;
      default:
        return null;
    }
  }

}
