# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'msgpack/rpc/version'

Gem::Specification.new do |spec|
  spec.name          = "msgpack-rpc-jruby"
  spec.version       = MessagePack::RPC::VERSION
  spec.authors       = ["k-yamada"]
  spec.email         = ["yamadakazu45@gmail.com"]
  spec.summary       = "MessagePack-RPC, asynchronous RPC library using MessagePack"
  spec.homepage      = "https://github.com/k-yamada/msgpack-rpc-ruby"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0")
  spec.executables   = spec.files.grep(%r{^bin/}) { |f| File.basename(f) }
  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["lib"]

  spec.add_dependency 'msgpack-jruby'

  spec.add_development_dependency "bundler", "~> 1.5"
  spec.add_development_dependency "rake"
end
