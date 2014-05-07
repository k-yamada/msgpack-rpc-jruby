require 'rubygems'
require 'bundler/setup'

require 'rspec/mocks'
$LOAD_PATH.unshift(File.expand_path(File.join(File.dirname(__FILE__), '..', '..', 'lib')))
require 'msgpack'
require 'msgpack/rpc'
