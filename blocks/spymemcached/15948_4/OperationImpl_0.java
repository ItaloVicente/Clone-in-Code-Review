
package net.spy.memcached.ops;

public enum ErrorCode {

  SUCCESS((short) 0x00),

  ERR_NOT_FOUND((short) 0x01),

  ERR_EXISTS((short) 0x02),

  ERR_2BIG((short) 0x03),

  ERR_INVAL((short) 0x04),

  ERR_NOT_STORED((short) 0x05),

  ERR_DELTA_BADVAL((short) 0x06),

  ERR_NOT_MY_VBUCKET((short) 0x07),

  ERR_AUTH_ERROR((short) 0x20),

  ERR_AUTH_CONTINUE((short) 0x21),
  ERR_UNKNOWN_COMMAND((short) 0x81),

  ERR_NO_MEM((short) 0x82),

  ERR_NOT_SUPPORTED((short) 0x83),

  ERR_INTERNAL((short) 0x84),

  ERR_BUSY((short) 0x85),

  ERR_TEMP_FAIL((short) 0x86),

  UNKNOWN_ERROR((short) 0xF0),

  CANCELLED((short) 0xF1),

  TIMED_OUT((short) 0xF2),

  EXCEPTION((short) 0xF3);

  private final short error;

  ErrorCode(short err) {
    error = err;
  }

  public static ErrorCode getErrorCode(short b) {
    if (b == ErrorCode.SUCCESS.error) {
      return ErrorCode.SUCCESS;
    } else if (b == ErrorCode.ERR_NOT_FOUND.error) {
      return ErrorCode.ERR_NOT_FOUND;
    } else if (b == ErrorCode.ERR_EXISTS.error) {
      return ErrorCode.ERR_EXISTS;
    } else if (b == ErrorCode.ERR_2BIG.error) {
      return ErrorCode.ERR_2BIG;
    } else if (b == ErrorCode.ERR_INVAL.error) {
      return ErrorCode.ERR_INVAL;
    } else if (b == ErrorCode.ERR_NOT_STORED.error) {
      return ErrorCode.ERR_NOT_STORED;
    } else if (b == ErrorCode.ERR_DELTA_BADVAL.error) {
      return ErrorCode.ERR_DELTA_BADVAL;
    } else if (b == ErrorCode.ERR_NOT_MY_VBUCKET.error) {
      return ErrorCode.ERR_NOT_MY_VBUCKET;
    } else if (b == ErrorCode.ERR_AUTH_ERROR.error) {
      return ErrorCode.ERR_AUTH_ERROR;
    } else if (b == ErrorCode.ERR_AUTH_CONTINUE.error) {
      return ErrorCode.ERR_AUTH_CONTINUE;
    }  else if (b == ErrorCode.ERR_UNKNOWN_COMMAND.error) {
      return ErrorCode.ERR_UNKNOWN_COMMAND;
    }  else if (b == ErrorCode.ERR_NO_MEM.error) {
      return ErrorCode.ERR_NO_MEM;
    }  else if (b == ErrorCode.ERR_NOT_SUPPORTED.error) {
      return ErrorCode.ERR_NOT_SUPPORTED;
    }  else if (b == ErrorCode.ERR_INTERNAL.error) {
      return ErrorCode.ERR_INTERNAL;
    }  else if (b == ErrorCode.ERR_BUSY.error) {
      return ErrorCode.ERR_BUSY;
    }  else if (b == ErrorCode.ERR_TEMP_FAIL.error) {
      return ErrorCode.ERR_TEMP_FAIL;
    }
    return ErrorCode.UNKNOWN_ERROR;
  }
}
