require "bundler/gem_tasks"
require "pp"

task :default => :spec

#RSpec::Core::RakeTask.new(:spec) do |r|
#  r.rspec_opts = '--tty'
#end
#
task :spec => :package

task :clean do
  rm Dir['ext/java/**/*.class']
  rm "lib/ext/msgpack_rpc_jruby.jar"
end

task :compile do
  jruby_home = "#{ENV['HOME']}/.rbenv/versions/#{ENV['RBENV_VERSION']}"
  classpath = (Dir["lib/ext/*.jar"] + ["#{jruby_home}/lib/jruby.jar"]).join(':')
  system %(javac -Xlint:-options -deprecation -source 1.6 -target 1.6 -cp #{classpath} ext/java/*.java ext/java/org/msgpack/rpc/jruby/*.java)
  exit($?.exitstatus) unless $?.success?
end

task :package => :compile do
  class_files = Dir['ext/java/**/*.class'].map { |path| path = path.sub('ext/java/', ''); "-C ext/java '#{path}'" }
  p "==class_files=="
  pp class_files
  system %(jar cf lib/ext/msgpack_rpc_jruby.jar #{class_files.join(' ')})
  exit($?.exitstatus) unless $?.success?
end

task :release => :package do
  version_string = "v#{MessagePack::VERSION}"
  unless %x(git tag -l).split("\n").include?(version_string)
    system %(git tag -a #{version_string} -m #{version_string})
  end
  system %(gem build msgpack-jruby.gemspec && gem push msgpack-jruby-*.gem && mv msgpack-jruby-*.gem pkg)
end

namespace :benchmark do
  BENCHMARK_RUBIES = ['1.9.2-p0', 'jruby-1.6.5', 'jruby-head']
  BENCHMARK_GEMSET = 'msgpack-jruby-benchmarking'
  BENCHMARK_FILE = 'spec/benchmarks/shootout_bm.rb'

  task :run do
    rubies = BENCHMARK_RUBIES.map { |rb| "#{rb}@#{BENCHMARK_GEMSET}" }
    cmd = %(rvm #{rubies.join(',')} exec viiite run #{BENCHMARK_FILE} | tee benchmark | viiite report --hierarchy --regroup=bench,lib,ruby)
    puts cmd
    system cmd
  end

  task :quick do
    cmd = %(IMPLEMENTATIONS=msgpack viiite run #{BENCHMARK_FILE} | viiite report --hierarchy --regroup=bench)
    puts cmd
    system cmd
  end

  task :setup do
    rubies = BENCHMARK_RUBIES.map { |rb| "#{rb}@#{BENCHMARK_GEMSET}" }
    rubies.each do |ruby_version|
      cmd = %(rvm-shell #{ruby_version} -c 'bundle check || bundle install')
      puts cmd
      system cmd
    end
  end
end
