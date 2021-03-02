require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = 'react-native-fawry-sdk'
  s.version      = package['version']
  s.summary      = package['description']
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.authors      = package['author']

  s.platforms    = { ios: '10.0' }
  s.source       = { git: 'https://github.com/MahmoudAliIbrahim/react-native-fawry-sdk.git', tag: s.version.to_s }

  s.vendored_frameworks = 'ios/MyFawryPlugin.framework'
  s.source_files = 'ios/**/*.{h,m,mm,swift}'
  s.requires_arc = true
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES' }
  s.dependency 'React'
  s.swift_version = '4.0'
end
