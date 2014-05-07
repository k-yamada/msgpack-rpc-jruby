package org.msgpack.rpc.jruby;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyModule;
import org.jruby.RubyClass;
import org.jruby.RubyString;
import org.jruby.RubyObject;
import org.jruby.RubyHash;
import org.jruby.RubyIO;
import org.jruby.RubyStringIO;
import org.jruby.RubyNumeric;
import org.jruby.RubyEnumerator;
import org.jruby.runtime.load.Library;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.Arity;
import org.jruby.runtime.Block;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.anno.JRubyClass;
import org.jruby.anno.JRubyModule;
import org.jruby.anno.JRubyMethod;
import org.jruby.util.IOInputStream;

import static org.jruby.runtime.Visibility.*;

import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.MessagePackBufferUnpacker;
import org.msgpack.unpacker.MessagePackUnpacker;
import org.msgpack.unpacker.UnpackerIterator;
import org.msgpack.type.Value;
import org.msgpack.io.Input;
import org.msgpack.io.LinkedBufferInput;
import org.msgpack.io.StreamInput;

import org.msgpack.rpc.*;
import org.msgpack.rpc.Server;
import org.msgpack.rpc.loop.EventLoop;


public class MessagePackRPCLibrary implements Library {
  public void load(Ruby runtime, boolean wrap) throws IOException {
    RubyModule msgpackRPCModule = runtime.defineModule("MessagePack").defineModuleUnder("RPC");
    msgpackRPCModule.defineAnnotatedMethods(MessagePackRPCModule.class);
    Server server = new Server();
    RubyClass serverClass = msgpackRPCModule.defineClassUnder("Server", runtime.getObject(), new ServerAllocator(server));
    serverClass.defineAnnotatedMethods(RPCServer.class);

    //Client client = new Client();
    RubyClass clientClass = msgpackRPCModule.defineClassUnder("Client", runtime.getObject(), new ClientAllocator());
    clientClass.defineAnnotatedMethods(RPCClient.class);

    msgpackRPCModule.defineModuleUnder("Test");
  }

  @JRubyModule(name = "MessagePack::RPC")
  public static class MessagePackRPCModule {
  }

  private static class ServerAllocator implements ObjectAllocator {
    private Server server;

    public ServerAllocator(Server server) {
      this.server = server;
    }

    public IRubyObject allocate(Ruby runtime, RubyClass klass) {
      return new RPCServer(runtime, klass, server);
    }
  }

  @JRubyClass(name="MessagePack::RPC::Server")
  public static class RPCServer extends RubyObject {
    private Server server;

    public RPCServer(Ruby runtime, RubyClass type, Server server) {
      super(runtime, type);
      this.server = server;
    }

    @JRubyMethod(name = "initialize", optional = 2, visibility = PRIVATE)
    public IRubyObject initialize(ThreadContext ctx, IRubyObject[] args) {
      return this;
    }
  }

  private static class ClientAllocator implements ObjectAllocator {
    private Client client;

    public ClientAllocator() {
      //this.client = client;
    }

    public IRubyObject allocate(Ruby runtime, RubyClass klass) {
      return new RPCClient(runtime, klass);
    }
  }

  @JRubyClass(name="MessagePack::RPC::Client")
  public static class RPCClient extends RubyObject {
    //private Client client;

    public RPCClient(Ruby runtime, RubyClass type) {
      super(runtime, type);
      //this.client = client;
    }

    @JRubyMethod(name = "initialize", optional = 2, visibility = PRIVATE)
    public IRubyObject initialize(ThreadContext ctx, IRubyObject[] args) {
      return this;
    }
  }
}
