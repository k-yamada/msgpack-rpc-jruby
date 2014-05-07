import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.runtime.load.BasicLibraryService;

import org.msgpack.rpc.jruby.MessagePackRPCLibrary;


public class MsgpackRPCJrubyService implements BasicLibraryService {
  public boolean basicLoad(final Ruby runtime) throws IOException {
    new MessagePackRPCLibrary().load(runtime, false);
    return true;
  }
}
